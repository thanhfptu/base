package vn.edu.fpt.capstone.api.services.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.Semester;
import vn.edu.fpt.capstone.api.entities.SemesterLineManager;
import vn.edu.fpt.capstone.api.models.OTPUserDetails;
import vn.edu.fpt.capstone.api.repositories.CompanyContactRepository;
import vn.edu.fpt.capstone.api.repositories.SemesterLineManagerRepository;
import vn.edu.fpt.capstone.api.repositories.SemesterRepository;
import vn.edu.fpt.capstone.api.requests.OTPAuthenticationRequest;
import vn.edu.fpt.capstone.api.requests.OTPRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.OTPAuthenticationResponse;
import vn.edu.fpt.capstone.api.responses.OTPResponse;
import vn.edu.fpt.capstone.api.services.jwt.JWTService;
import vn.edu.fpt.capstone.api.services.mail.MailService;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service(OTPService.SERVICE_NAME)
public class OTPServiceImpl implements OTPService {

    protected static final long EXPIRATION = 1; // days
    protected static final int OTP_LENGTH = 6;
    protected static final String[] OTP_CHARACTERS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private final JWTService jwtService;
    private final MailService mailService;
    private final SemesterRepository semesterRepository;
    private final CompanyContactRepository companyContactRepository;
    private final SemesterLineManagerRepository semesterLineManagerRepository;

    @Override
    public String generate() {
        String otp = null;
        try {
            int length = OTP_CHARACTERS.length;
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            StringBuilder otpBuilder = new StringBuilder();
            for (int i = 0; i < OTP_LENGTH; i++) {
                int index = random.nextInt(length);
                otpBuilder.append(OTP_CHARACTERS[index]);
            }
            otp = otpBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Có lỗi xảy ra khi tạo OTP, chi tiết: {}", e.getMessage());
        }
        return otp;
    }

    @Override
    public BaseResponse<OTPResponse> generateAndSend(OTPRequest request) {
        try {
            Long requestId = request.getRequestId();

            SemesterLineManager semesterLineManager = semesterLineManagerRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Id %s!", requestId)));

            if (Boolean.FALSE.equals(semesterLineManager.getEnabled())) {
                throw new IllegalArgumentException("Bạn không có quyền lấy mã OTP!");
            }

            Long semesterId = semesterLineManager.getSemesterId();

            Semester semester = semesterRepository.findById(semesterId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Id %s!", semesterId)));

            if (Boolean.FALSE.equals(semester.getIsActive())) {
                throw new IllegalArgumentException("Bạn không thể lấy OTP do: Kỳ học đã đóng!");
            }

            Long lineManagerId = semesterLineManager.getLineManagerId();

            CompanyContact lineManager = companyContactRepository.findById(lineManagerId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy người quản lý với Id %s!", lineManagerId)));

            if (Objects.isNull(semesterLineManager.getIssuedAt()) || DateUtils.of(DateUtils.toLocalDateTime(semesterLineManager.getIssuedAt()).plusDays(EXPIRATION)).before(DateUtils.now())) {
                semesterLineManager.setOtp(generate());
                semesterLineManager.setEnabled(true);
                semesterLineManager.setIssuedAt(DateUtils.now());
                semesterLineManager = semesterLineManagerRepository.save(semesterLineManager);
            }

            String otp = semesterLineManager.getOtp();

            String email = lineManager.getEmail();

            mailService.send(email, "Yêu cầu lấy mã OTP", String.format("Mã OTP của bạn là: %s", otp));

            return BaseResponse.success(OTPResponse.of(requestId, email));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<OTPAuthenticationResponse> authenticate(OTPAuthenticationRequest request) {
        try {
            Long requestId = request.getRequestId();

            SemesterLineManager semesterLineManager = semesterLineManagerRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy yêu cầu đánh giá với Id %s!", requestId)));

            Long lineManagerId = semesterLineManager.getLineManagerId();

            CompanyContact contact = companyContactRepository.findById(lineManagerId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy người quản lý với Id %s!", lineManagerId)));

            String email = contact.getEmail();

            String otp = semesterLineManager.getOtp();

            if (!otp.equals(request.getOtp())) {
                throw new IllegalArgumentException("Mã OTP không hợp lệ!");
            }

            String accessToken = jwtService.generateToken(email);

            OTPAuthenticationResponse data = OTPAuthenticationResponse.of(requestId, email, accessToken);

            return BaseResponse.success(data);
        } catch (IllegalArgumentException ex) {
            return BaseResponse.error(String.format("Có lỗi xảy ra khi xác thực OTP, chi tiết: %s", ex.getMessage()));
        }
    }

    @Override
    public BaseResponse<OTPResponse> get(Long requestId) {
        try {

            SemesterLineManager semesterLineManager = semesterLineManagerRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy yêu cầu đánh giá với Id %s!", requestId)));

            Long lineManagerId = semesterLineManager.getLineManagerId();

            CompanyContact contact = companyContactRepository.findById(lineManagerId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy người quản lý với Id %s!", lineManagerId)));

            return BaseResponse.success(OTPResponse.of(requestId, contact.getEmail()));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CompanyContact contact = companyContactRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Không tìm thấy người quản lý với email %s!", email)));
        return new OTPUserDetails(contact);
    }

}
