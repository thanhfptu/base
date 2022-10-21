package vn.edu.fpt.capstone.api.services.ojtinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.entities.dto.OJTInfoDTO;
import vn.edu.fpt.capstone.api.repositories.*;
import vn.edu.fpt.capstone.api.requests.OJTInfoRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service(OJTInfoService.SERVICE_NAME)
public class OJTInfoServiceImpl implements OJTInfoService {

    private final OJTInfoRepository ojtInfoRepository;

    private final RequestAddCompanyRepository requestAddCompanyRepository;

    private final RequestAddCompanyContactRepository requestAddCompanyContactRepository;

    private final CompanyRepository companyRepository;

    private final CompanyContactRepository companyContactRepository;

    private final SemesterRepository semesterRepository;

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    @Override
    public PageResponse<OJTInfoDTO> list(Pageable pageable,
                                         String taxCode,
                                         Long companyId,
                                         Long contactId,
                                         String studentName,
                                         String requestTaxCode,
                                         String companyName,
                                         String contactName,
                                         String requestCompanyName,
                                         String requestContactName,
                                         Integer status,
                                         String position,
                                         Long semesterId) {
        StringBuilder selectBuild = new StringBuilder("SELECT ")
                .append("ojt.id as id, ")
                .append("company.name as company_name, ")
                .append("company.tax_code as tax_code, ")
                .append("a.id as add_company_id, ")
                .append("a.name as request_company_name, ")
                .append("a.status as add_company_status, ")
                .append("a.tax_code as request_tax_code, ")
                .append("contact.name as company_contact, ")
                .append("accr.id as add_company_contact_id, ")
                .append("accr.status as add_company_contact_status, ")
                .append("accr.name as request_company_contact, ")
                .append("student.id as student_id, ")
                .append("student.full_name as student_name, ")
                .append("ojt.position as position, ")
                .append("ojt.status as status, ")
                .append("ojt.semester_id as semester_id, ")
                .append("semester.name as semester_name ");

        StringBuilder fromBuild = new StringBuilder("From ojt_information_registration ojt ")
                .append("left join companies company on ojt.company_id = company.id ")
                .append("left join company_contacts contact on ojt.company_contact_id = contact.id ")
                .append("left join add_company_contact_request accr on ojt.add_company_contact_request_id = accr.id ")
                .append("left join add_company_request a on  ojt.add_company_request_id = a.id ")
                .append("left join users student on student.id = ojt.student_id ")
                .append("left join semesters semester on semester.id = ojt.semester_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(taxCode)) {
            whereBuild.append("and company.tax_code = :taxCode ");
            params.put("taxCode", taxCode);
        }

        if (StringUtils.hasText(requestTaxCode)) {
            whereBuild.append("and accr.tax_code = :requestTaxCode ");
            params.put("requestTaxCode", requestTaxCode);
        }

        if (StringUtils.hasText(companyName)) {
            whereBuild.append("and company.name like CONCAT('%', :companyName,'%') ");
            params.put("companyName", companyName);
        }

        if (StringUtils.hasText(contactName)) {
            whereBuild.append("and contact.name like CONCAT('%', :contactName,'%') ");
            params.put("contactName", contactName);
        }

        if (StringUtils.hasText(requestCompanyName)) {
            whereBuild.append("and a.name like CONCAT('%', :requestCompanyName,'%') ");
            params.put("requestCompanyName", requestCompanyName);
        }

        if (StringUtils.hasText(requestContactName)) {
            whereBuild.append("and accr.name like CONCAT('%', :requestContactName,'%') ");
            params.put("requestContactName", requestContactName);
        }

        if (StringUtils.hasText(studentName)) {
            whereBuild.append("and student.full_name like CONCAT('%', :studentName,'%') ");
            params.put("studentName", studentName);
        }

        if (StringUtils.hasText(position)) {
            whereBuild.append("and ojt.position like CONCAT('%', :position,'%') ");
            params.put("position", position);
        }

        if (Objects.nonNull(companyId)) {
            whereBuild.append("and ojt.company_id = :companyId ");
            params.put("companyId", companyId);
        }

        if (Objects.nonNull(contactId)) {
            whereBuild.append("and ojt.company_contact_id = :contactId ");
            params.put("contactId", contactId);
        }

        if (Objects.nonNull(status)) {
            whereBuild.append("and ojt.status = :status ");
            params.put("status", status);
        }

        if (Objects.nonNull(semesterId)) {
            whereBuild.append("and ojt.semester_id = :semesterId ");
            params.put("semesterId", semesterId);
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query count = entityManager.createNativeQuery(queryBuild, OJTInfoDTO.SQL_RESULT_SET_MAPPING);

        params.forEach(count::setParameter);

        List<OJTInfoDTO> total = count.getResultList();
        long totalEntities = total.size();
        long totalPage = (long) Math.ceil((double) totalEntities / pageable.getPageSize());

        queryBuild = queryBuild.concat("ORDER BY ojt.id desc LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(queryBuild, OJTInfoDTO.SQL_RESULT_SET_MAPPING)
                .setParameter("offset", pageable.getPageNumber() * pageable.getPageSize())
                .setParameter("limit", pageable.getPageSize());

        params.forEach(query::setParameter);

        List<OJTInfoDTO> data = query.getResultList();

        return PageResponse.success(data, pageable.getPageNumber(), pageable.getPageSize(), totalEntities, totalPage);
    }

    @Override
    public BaseResponse<OJTInfoResponse> get(Long id) {
        try {
            OJTInfo ojtInfo = ojtInfoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đăng ký OJT với id %s", id)));
            Company company = Objects.isNull(ojtInfo.getCompanyId())
                    ? null
                    : companyRepository.findById(ojtInfo.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy công ty với id %s", ojtInfo.getCompanyId())));

            CompanyContact companyContact = Objects.isNull(ojtInfo.getCompanyContactId())
                    ? null
                    : companyContactRepository.findById(ojtInfo.getCompanyContactId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy contact với id %s", ojtInfo.getCompanyContactId())));

            RequestAddCompany requestCompany = Objects.isNull(ojtInfo.getRequestAddCompanyId())
                    ? null
                    : requestAddCompanyRepository.findById(ojtInfo.getRequestAddCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy yêu cầu tạo công ty với id %s", ojtInfo.getRequestAddCompanyId())));

            RequestAddCompanyContact requestCompanyContact = Objects.isNull(ojtInfo.getRequestAddCompanyContactId())
                    ? null
                    : requestAddCompanyContactRepository.findById(ojtInfo.getRequestAddCompanyContactId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy yêu cầy tạo contact với id %s", ojtInfo.getRequestAddCompanyContactId())));

            User student = userRepository.findById(ojtInfo.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy student với id %s!", ojtInfo.getStudentId())));

            Semester semester = semesterRepository.findById(ojtInfo.getSemesterId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy semester với id %s", ojtInfo.getSemesterId())));

            return response(ojtInfo, company, companyContact, requestCompany, requestCompanyContact, student, semester);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<OJTInfoResponse> create(OJTInfoRequest request, Company company, CompanyContact companyContact, RequestAddCompany requestAddCompany, RequestAddCompanyContact requestAddCompanyContact,User student, Semester semester, Long operatorId) {

        OJTInfo ojtInfo = OJTInfo.of(request, operatorId);
        ojtInfo.setStatus(OJTInfoStatus.UNCHECKED);

        ojtInfo.setSemesterId(semester.getId());

        if (Objects.nonNull(requestAddCompany)) {
            requestAddCompany.setStatus(OJTInfoStatus.UNCHECKED);
            requestAddCompany = requestAddCompanyRepository.save(requestAddCompany);
            ojtInfo.setRequestAddCompanyId(requestAddCompany.getId());
        }

        if (Objects.nonNull(requestAddCompanyContact)) {
            requestAddCompanyContact.setStatus(OJTInfoStatus.UNCHECKED);
            requestAddCompanyContact = requestAddCompanyContactRepository.save(requestAddCompanyContact);
            ojtInfo.setRequestAddCompanyContactId(requestAddCompanyContact.getId());
        }

        ojtInfo = ojtInfoRepository.save(ojtInfo);

        return response(ojtInfo, company, companyContact, requestAddCompany, requestAddCompanyContact, student, semester);
    }

    @Override
    public BaseResponse<OJTInfoResponse> update(OJTInfoRequest request, Company company, CompanyContact companyContact, RequestAddCompany requestAddCompany, RequestAddCompanyContact requestAddCompanyContact, User student, Semester semester, Long operatorId) {
        try {
            Long id = request.getId();
            OJTInfo ojtInfo = ojtInfoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đăng ký OJT với id %s", id)));
            ojtInfo.setCompanyId(request.getCompanyId());
            ojtInfo.setCompanyContactId(request.getCompanyContactId());
            ojtInfo.setModifiedAt(DateUtils.now());
            ojtInfo.setStatus(OJTInfoStatus.UNCHECKED);
            ojtInfo.setModifiedBy(operatorId);

            if (Objects.nonNull(requestAddCompany)) {
                requestAddCompany.setStatus(OJTInfoStatus.UNCHECKED);
                requestAddCompany = requestAddCompanyRepository.save(requestAddCompany);
                ojtInfo.setRequestAddCompanyId(requestAddCompany.getId());
            }

            if (Objects.nonNull(requestAddCompanyContact)) {
                requestAddCompanyContact.setStatus(OJTInfoStatus.UNCHECKED);
                requestAddCompanyContact = requestAddCompanyContactRepository.save(requestAddCompanyContact);
                ojtInfo.setRequestAddCompanyContactId(requestAddCompanyContact.getId());
            }

            ojtInfo = ojtInfoRepository.save(ojtInfo);

            return response(ojtInfo, company, companyContact, requestAddCompany, requestAddCompanyContact, student, semester);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<OJTInfoResponse> save(OJTInfoRequest request, Long operatorId) {
        try {
            RequestAddCompany requestAddCompany = Objects.isNull(request.getRequestAddCompany())
                    ? null
                    : RequestAddCompany.of(request.getRequestAddCompany());

            RequestAddCompanyContact requestAddCompanyContact = Objects.isNull(request.getRequestAddCompanyContact())
                    ? null
                    : RequestAddCompanyContact.of(request.getRequestAddCompanyContact());

            Company company = Objects.isNull(request.getCompanyId())
                    ? null
                    : companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy công ty với id %s", request.getCompanyId())));

            CompanyContact companyContact = Objects.isNull(request.getCompanyContactId())
                    ? null
                    : companyContactRepository.findById(request.getCompanyContactId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy contact với id %s", request.getCompanyContactId())));

            User student = userRepository.findById(operatorId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy student với id %s!", operatorId)));

            Semester semester = semesterRepository.getCurrentSemester();

            if(Objects.isNull(semester)){
                throw new IllegalArgumentException("Có lỗi bản ghi với Semester!");
            }

            return Objects.isNull(request.getId())
                    ? create(request, company, companyContact, requestAddCompany, requestAddCompanyContact, student, semester, operatorId)
                    : update(request, company, companyContact, requestAddCompany, requestAddCompanyContact, student, semester, operatorId );
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<OJTInfoResponse> response(OJTInfo ojtInfo, Company company, CompanyContact companyContact, RequestAddCompany requestAddCompany, RequestAddCompanyContact requestAddCompanyContact, User student, Semester semester) {
        CompanyResponse companyResponse = Objects.isNull(company)
                ? null
                : CompanyResponse.of(company);

        CompanyContactResponse companyContactResponse = Objects.isNull(companyContact)
                ? null
                : CompanyContactResponse.of(companyContact);

        RequestAddCompanyResponse requestAddCompanyResponse = Objects.isNull(requestAddCompany)
                ? null
                : RequestAddCompanyResponse.of(requestAddCompany);

        RequestAddCompanyContactResponse requestAddCompanyContactResponse = Objects.isNull(requestAddCompanyContact)
                ? null
                : RequestAddCompanyContactResponse.of(requestAddCompanyContact);

        SemesterResponse semesterResponse = Objects.isNull(semester)
                ? null
                : SemesterResponse.of(semester);

        UserResponse studentResponse = UserResponse.of(student);

        return BaseResponse.success(OJTInfoResponse.of(ojtInfo, companyResponse, companyContactResponse, requestAddCompanyContactResponse, requestAddCompanyResponse, studentResponse, semesterResponse));
    }

    @Override
    public BaseResponse<List<OJTInfoResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId) {
        try {
            List<OJTInfo> ojtInfos = ojtInfoRepository.findByIdIn(ids);

            if (ojtInfos.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            ojtInfos = ojtInfos.stream()
                    .map(ojtInfo -> {
                        ojtInfo.setStatus(OJTInfoStatus.NEED_VALIDATION.equals(ojtInfo.getStatus())
                                ? OJTInfoStatus.NEED_VALIDATION
                                : OJTInfoStatus.of(status));
                        ojtInfo.setModifiedBy(operatorId);
                        ojtInfo.setModifiedAt(DateUtils.now());
                        return ojtInfo;
                    })
                    .toList();
            ojtInfos = ojtInfoRepository.saveAll(ojtInfos);
            List<OJTInfoResponse> response = ojtInfos.stream().map(OJTInfoResponse::of).toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }
}
