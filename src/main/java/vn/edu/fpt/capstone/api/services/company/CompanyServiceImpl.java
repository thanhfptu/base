package vn.edu.fpt.capstone.api.services.company;

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
import vn.edu.fpt.capstone.api.entities.UserRole;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.CompanyContactRepository;
import vn.edu.fpt.capstone.api.repositories.CompanyRepository;
import vn.edu.fpt.capstone.api.requests.CompanyContactRequest;
import vn.edu.fpt.capstone.api.requests.CompanyRequest;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
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
@Service(CompanyService.SERVICE_NAME)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyContactRepository companyContactRepository;

    @Override
    public PageResponse<CompanyResponse> list(Pageable pageable,
                                              String name,
                                              String address,
                                              String description,
                                              String website,
                                              String imgUrl,
                                              String taxCode,
                                              Boolean enabled) {
        BaseSpecification<Company> specification = new BaseSpecification<>();

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(address)) {
            specification.add(new SearchCriteria("address", address, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(description)) {
            specification.add(new SearchCriteria("description", description, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(imgUrl)) {
            specification.add(new SearchCriteria("imgUrl", imgUrl, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(taxCode)) {
            specification.add(new SearchCriteria("taxCode", taxCode, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(website)) {
            specification.add(new SearchCriteria("website", website, SearchOperation.MATCH));
        }
        if (Objects.nonNull(enabled)) {
            specification.add(new SearchCriteria("enabled", enabled, SearchOperation.EQUAL));
        }

        Page<Company> page = companyRepository.findAll(specification, pageable);

        List<Company> content = CollectionUtils.isEmpty(page.getContent()) ? Collections.emptyList() : page.getContent();

        List<CompanyResponse> data = content.stream()
                .map(CompanyResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<CompanyResponse> get(Long id) {
        try {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company với id %s", id)));

            List<CompanyContact> companyContacts = companyContactRepository.findByCompanyId(id);

            if (CollectionUtils.isEmpty(companyContacts)) {
                return response(company, Collections.emptyList());
            }

            return response(company, companyContacts);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyResponse> save(CompanyRequest request, Long operatorId) {
        try {
            return Objects.isNull(request.getId()) ? create(request, operatorId) : update(request, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyResponse> create(CompanyRequest request, Long operatorId) {

        Company company = companyRepository.findByTaxCode(request.getTaxCode())
                .orElse(null);

        if (Objects.nonNull(company)) {
            return BaseResponse.error(String.format("Đã tồn tại công ty với mã số thuế %s!", company.getTaxCode()));
        }

        company = Company.of(request, operatorId);

        company = companyRepository.save(company);

        Long id = company.getId();

        if (CollectionUtils.isEmpty(request.getContacts())) {
            return response(company, Collections.emptyList());
        }

        List<CompanyContact> contacts = request.getContacts()
                .stream()
                .map(item -> {
                    CompanyContact contact = CompanyContact.of(item, operatorId);
                    contact.setCompanyId(id);
                    return contact;
                })
                .toList();

        contacts = companyContactRepository.saveAll(contacts);

        return response(company, contacts);
    }

    @Override
    public BaseResponse<CompanyResponse> update(CompanyRequest request, Long operatorId) {
        try {
            Company company = companyRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company với id %s", request.getId())));

            company.setAddress(request.getAddress());
            company.setName(request.getName());
            company.setTaxCode(request.getTaxCode());
            company.setEnabled(request.getEnabled());
            company.setWebsite(request.getWebsite());
            company.setDescription(request.getDescription());
            company.setImgUrl(request.getImgUrl());
            company.setModifiedAt(DateUtils.now());
            company.setModifiedBy(operatorId);

            company = companyRepository.save(company);

            Long id = company.getId();

            List<CompanyContactRequest> requestContacts = request.getContacts();

            if (CollectionUtils.isEmpty(requestContacts)) requestContacts = Collections.emptyList();

            List<Long> requestContactIds = requestContacts.stream().map(CompanyContactRequest::getId).toList();

            List<CompanyContact> currentContacts = companyContactRepository.findByCompanyId(id);

            if (CollectionUtils.isEmpty(currentContacts)) currentContacts = Collections.emptyList();

            List<Long> currentContactIds = currentContacts.stream().map(CompanyContact::getId).toList();

            List<CompanyContact> removedContacts = currentContacts.stream()
                    .filter(item -> !requestContactIds.contains(item.getId()))
                    .toList();

            if (!CollectionUtils.isEmpty(removedContacts)) {
                companyContactRepository.deleteAll(removedContacts);
            }

            List<CompanyContact> newContacts = requestContacts.stream()
                    .filter(item -> !currentContactIds.contains(item.getId()))
                    .map(item -> {
                        CompanyContact contact = CompanyContact.of(item, operatorId);
                        contact.setCompanyId(id);
                        return contact;
                    })
                    .toList();

            if (!CollectionUtils.isEmpty(newContacts)) {
                companyContactRepository.saveAll(newContacts);
            }

            List<CompanyContact> contacts = companyContactRepository.findByCompanyId(id);

            return response(company, contacts);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CompanyResponse> response(Company company, List<CompanyContact> companyContacts) {
        List<CompanyContactResponse> contactResponses = CollectionUtils.isEmpty(companyContacts)
                ? Collections.emptyList()
                : companyContacts.stream().map(CompanyContactResponse::of).toList();
        return BaseResponse.success(CompanyResponse.of(company, contactResponses));
    }

    @Override
    public BaseResponse<List<CompanyResponse>> disable(IdsRequest request, Long operatorId) {
        try {
            List<Long> ids = request.getIds();
            List<Company> companies = companyRepository.findByIdIn(ids);
            if (ids.size() != companies.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }
            companies = companies.stream()
                    .map((company) -> {
                        company.setEnabled(false);
                        company.setModifiedBy(operatorId);
                        company.setModifiedAt(DateUtils.now());
                        return company;
                    })
                    .toList();
            companies = companyRepository.saveAll(companies);
            return BaseResponse.success(companies.stream().map(CompanyResponse::of).toList());
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
