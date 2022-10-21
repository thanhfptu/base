package vn.edu.fpt.capstone.api.services.requestaddcompany;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyResponse;

import java.util.List;

public interface RequestAddCompanyService {
    String SERVICE_NAME = "RequestAddCompanyService";

    PageResponse<RequestAddCompanyResponse> list(Pageable pageable,
                                                 String taxCode,
                                                 String name,
                                                 Long createdBy);

    BaseResponse<RequestAddCompanyResponse> get(Long id);

    BaseResponse<RequestAddCompanyResponse> create(RequestAddCompanyRequest request, Long operatorId);

    BaseResponse<RequestAddCompanyResponse> update(RequestAddCompanyRequest request, Long operatorId);

    BaseResponse<RequestAddCompanyResponse> save(RequestAddCompanyRequest request, Long operatorId);

    BaseResponse<List<RequestAddCompanyResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId);
}
