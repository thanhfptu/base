package vn.edu.fpt.capstone.api.services.companyContact;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.Company;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.requests.CompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CompanyContactResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

public interface CompanyContactService {
    String SERVICE_NAME = "CompanyContactService";

    PageResponse<CompanyContactResponse> list(Pageable pageable,
                                              String email,
                                              String phone,
                                              String name,
                                              Long companyId,
                                              Boolean enabled);

    BaseResponse<CompanyContactResponse> get(Long id);

    BaseResponse<CompanyContactResponse> create(CompanyContactRequest request, Company company, User operator);

    BaseResponse<CompanyContactResponse> update(CompanyContactRequest request, Company company, User operator);

    BaseResponse<CompanyContactResponse> save(CompanyContactRequest request, User operator);

    BaseResponse<CompanyContactResponse> disable(Long id, User operator);

    BaseResponse<CompanyContactResponse> response(CompanyContact companyContact, Company company);
}
