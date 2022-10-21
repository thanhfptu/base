package vn.edu.fpt.capstone.api.services.companyContact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Company;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.CompanyContactRepository;
import vn.edu.fpt.capstone.api.repositories.CompanyRepository;
import vn.edu.fpt.capstone.api.requests.CompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CompanyContactResponse;
import vn.edu.fpt.capstone.api.responses.CompanyResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service(CompanyContactService.SERVICE_NAME)
public class CompanyContactServiceImpl implements CompanyContactService {

    private final CompanyContactRepository companyContactRepository;

    private final CompanyRepository companyRepository;

    @Override
    public PageResponse<CompanyContactResponse> list(Pageable pageable,
                                                     String email,
                                                     String phone,
                                                     String name,
                                                     Long companyId,
                                                     Boolean enabled) {
        BaseSpecification<CompanyContact> specification = new BaseSpecification<>();

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(email)) {
            specification.add(new SearchCriteria("email", email, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(phone)) {
            specification.add(new SearchCriteria("phone", phone, SearchOperation.MATCH));
        }
        if (Objects.nonNull(companyId)) {
            specification.add(new SearchCriteria("companyId", companyId, SearchOperation.EQUAL));
        }
        if (Objects.nonNull(enabled)) {
            specification.add(new SearchCriteria("enabled", enabled, SearchOperation.EQUAL));
        }

        Page<CompanyContact> page = companyContactRepository.findAll(specification, pageable);

        List<CompanyContact> content = CollectionUtils.isEmpty(page.getContent())
                ? Collections.emptyList() : page.getContent();

        List<CompanyContactResponse> data = content.stream()
                .map(CompanyContactResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<CompanyContactResponse> get(Long id) {
        try {
            CompanyContact companyContact = companyContactRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company contact với id %s", id)));

            Company company = companyRepository.findById(companyContact.getCompanyId()).orElse(null);

            return response(companyContact, company);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyContactResponse> create(CompanyContactRequest request,
                                                       Company company,
                                                       User operator) {
        CompanyContact companyContact = CompanyContact.of(request, operator.getId());

        companyContact = companyContactRepository.save(companyContact);

        return response(companyContact, company);
    }

    @Override
    public BaseResponse<CompanyContactResponse> update(CompanyContactRequest request,
                                                       Company company,
                                                       User operator) {
        try {
            CompanyContact companyContact = companyContactRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company contact với id %s", request.getId())));

            companyContact.setEmail(request.getEmail());
            companyContact.setName(request.getName());
            companyContact.setPhone(request.getPhone());
            companyContact.setModifiedAt(DateUtils.now());
            companyContact.setModifiedBy(operator.getId());

            companyContact = companyContactRepository.save(companyContact);

            return response(companyContact, company);

        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyContactResponse> save(CompanyContactRequest request, User operator) {
        try {
            Long companyId = request.getCompanyId();

            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company với id %s", companyId)));

            return Objects.isNull(request.getId()) ? create(request, company, operator) : update(request, company, operator);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyContactResponse> disable(Long id, User operator) {
        try {
            CompanyContact companyContact = companyContactRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company contact với id %s", id)));

            companyContact.setModifiedAt(DateUtils.now());
            companyContact.setModifiedBy(operator.getId());

            companyContact = companyContactRepository.save(companyContact);

            Long companyId = companyContact.getCompanyId();

            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company với id %s", companyId)));

            return response(companyContact, company);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyContactResponse> response(CompanyContact companyContact, Company company) {
        CompanyResponse companyResponse = Objects.isNull(company) ? null : CompanyResponse.of(company);
        return BaseResponse.success(CompanyContactResponse.of(companyContact, companyResponse));
    }
}
