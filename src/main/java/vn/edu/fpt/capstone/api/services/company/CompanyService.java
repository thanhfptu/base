package vn.edu.fpt.capstone.api.services.company;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.Company;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.requests.CompanyRequest;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CompanyResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.List;

public interface CompanyService {
    String SERVICE_NAME = "CompanyService";

    PageResponse<CompanyResponse> list(Pageable pageable,
                                       String name,
                                       String address,
                                       String description,
                                       String website,
                                       String imgUrl,
                                       String taxCode,
                                       Boolean enabled);

    BaseResponse<CompanyResponse> get(Long id);

    BaseResponse<CompanyResponse> save(CompanyRequest request, Long operatorId);

    BaseResponse<CompanyResponse> create(CompanyRequest request, Long operatorId);

    BaseResponse<CompanyResponse> update(CompanyRequest request, Long operatorId);

    BaseResponse<CompanyResponse> response(Company company, List<CompanyContact> companyContacts);

    BaseResponse<List<CompanyResponse>> disable(IdsRequest request, Long operatorId);

}
