package vn.edu.fpt.capstone.api.services.otp;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.capstone.api.requests.OTPAuthenticationRequest;
import vn.edu.fpt.capstone.api.requests.OTPRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.OTPAuthenticationResponse;
import vn.edu.fpt.capstone.api.responses.OTPResponse;

public interface OTPService extends UserDetailsService {

    String SERVICE_NAME = "OTPService";

    String generate();

    BaseResponse<OTPResponse> generateAndSend(OTPRequest request);

    BaseResponse<OTPAuthenticationResponse> authenticate(OTPAuthenticationRequest request);

    BaseResponse<OTPResponse> get(Long requestId);

}
