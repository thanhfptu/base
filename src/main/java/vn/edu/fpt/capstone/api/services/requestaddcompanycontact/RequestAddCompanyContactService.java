package vn.edu.fpt.capstone.api.services.requestaddcompanycontact;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyContactResponse;

import java.util.List;

public interface RequestAddCompanyContactService {
    String SERVICE_NAME = "RequestAddCompanyContactService";

    PageResponse<RequestAddCompanyContactResponse> list(Pageable pageable,
                                                        String email,
                                                        String name,
                                                        String phone,
                                                        Long createdBy);

    BaseResponse<RequestAddCompanyContactResponse> get(Long id);

    BaseResponse<RequestAddCompanyContactResponse> create(RequestAddCompanyContactRequest request, Long operatorId);

    BaseResponse<RequestAddCompanyContactResponse> update(RequestAddCompanyContactRequest request, Long operatorId);

    BaseResponse<RequestAddCompanyContactResponse> save(RequestAddCompanyContactRequest request, Long operatorId);

    BaseResponse<List<RequestAddCompanyContactResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId);
}
