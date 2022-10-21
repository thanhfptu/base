package vn.edu.fpt.capstone.api.services.ojtinfo;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.entities.dto.OJTInfoDTO;
import vn.edu.fpt.capstone.api.requests.OJTInfoRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.OJTInfoResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.List;

public interface OJTInfoService {
    String SERVICE_NAME = "OJTInfoService";
    PageResponse<OJTInfoDTO> list(Pageable pageable,
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
                                  Long semesterId);

    BaseResponse<OJTInfoResponse> get(Long id);

    BaseResponse<OJTInfoResponse> create(OJTInfoRequest request,
                                         Company company,
                                         CompanyContact companyContact,
                                         RequestAddCompany requestAddCompany,
                                         RequestAddCompanyContact requestAddCompanyContact,
                                         User student,
                                         Semester semester,
                                         Long operatorId);

    BaseResponse<OJTInfoResponse> update(OJTInfoRequest request,
                                                   Company company,
                                                   CompanyContact companyContact,
                                                   RequestAddCompany requestAddCompany,
                                                   RequestAddCompanyContact requestAddCompanyContact,
                                                   User student,
                                                   Semester semester,
                                                   Long operatorId);

    BaseResponse<OJTInfoResponse> save(OJTInfoRequest request, Long operatorId);

    BaseResponse<OJTInfoResponse> response(OJTInfo ojtInfo,
                                           Company company,
                                           CompanyContact companyContact,
                                           RequestAddCompany requestAddCompany,
                                           RequestAddCompanyContact requestAddCompanyContact,
                                           User student,
                                           Semester semester);
    BaseResponse<List<OJTInfoResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId);
}
