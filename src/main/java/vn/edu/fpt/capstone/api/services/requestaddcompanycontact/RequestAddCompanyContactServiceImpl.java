package vn.edu.fpt.capstone.api.services.requestaddcompanycontact;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.RequestAddCompany;
import vn.edu.fpt.capstone.api.entities.RequestAddCompanyContact;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.RequestAddCompanyContactRepository;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyContactResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(RequestAddCompanyContactService.SERVICE_NAME)
public class RequestAddCompanyContactServiceImpl implements RequestAddCompanyContactService{

    private final RequestAddCompanyContactRepository requestAddCompanyContactRepository;

    @Override
    public PageResponse<RequestAddCompanyContactResponse> list(Pageable pageable, String email, String name, String phone, Long createdBy) {
        BaseSpecification<RequestAddCompanyContact> specification = new BaseSpecification<>();
        if (StringUtils.hasText(email)) {
            specification.add(new SearchCriteria("email", email, SearchOperation.LIKE));
        }
        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.LIKE));
        }
        if (StringUtils.hasText(phone)) {
            specification.add(new SearchCriteria("phone", phone, SearchOperation.LIKE));
        }
        if (Objects.nonNull(createdBy)) {
            specification.add(new SearchCriteria("createdBy", createdBy, SearchOperation.EQUAL));
        }
        Page<RequestAddCompanyContact> page = requestAddCompanyContactRepository.findAll(specification, pageable);

        List<RequestAddCompanyContact> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<RequestAddCompanyContactResponse> data = content.stream()
                .map(RequestAddCompanyContactResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<RequestAddCompanyContactResponse> get(Long id) {
        try {
            RequestAddCompanyContact requestAddCompanyContact = requestAddCompanyContactRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy request add company contact với id %s", id)));
            return BaseResponse.success(RequestAddCompanyContactResponse.of(requestAddCompanyContact));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RequestAddCompanyContactResponse> create(RequestAddCompanyContactRequest request, Long operatorId) {
        RequestAddCompanyContact requestAddCompanyContact = RequestAddCompanyContact.of(request, operatorId);
        requestAddCompanyContact = requestAddCompanyContactRepository.save(requestAddCompanyContact);
        return BaseResponse.success(RequestAddCompanyContactResponse.of(requestAddCompanyContact));
    }

    @Override
    public BaseResponse<RequestAddCompanyContactResponse> update(RequestAddCompanyContactRequest request, Long operatorId) {
        try {
            Long id = request.getId();
            RequestAddCompanyContact requestAddCompanyContact = requestAddCompanyContactRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy request add company contact với id %s", id)));
            requestAddCompanyContact.setModifiedAt(DateUtils.now());
            requestAddCompanyContact.setModifiedBy(operatorId);
            requestAddCompanyContact.setEmail(request.getEmail());
            requestAddCompanyContact.setName(request.getName());
            requestAddCompanyContact.setPhone(request.getPhone());
            requestAddCompanyContact = requestAddCompanyContactRepository.save(requestAddCompanyContact);
            return BaseResponse.success(RequestAddCompanyContactResponse.of(requestAddCompanyContact));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RequestAddCompanyContactResponse> save(RequestAddCompanyContactRequest request, Long operatorId) {
        return Objects.isNull(request.getId()) ? create(request, operatorId) : update(request, operatorId);
    }

    @Override
    public BaseResponse<List<RequestAddCompanyContactResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId) {
        try {
            List<RequestAddCompanyContact> requestAddCompanyContacts = requestAddCompanyContactRepository.findByIdIn(ids);

            if (requestAddCompanyContacts.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            requestAddCompanyContacts = requestAddCompanyContacts.stream()
                    .map(requestAddCompanyContact -> {
                        requestAddCompanyContact.setStatus(OJTInfoStatus.of(status));
                        requestAddCompanyContact.setModifiedBy(operatorId);
                        requestAddCompanyContact.setModifiedAt(DateUtils.now());
                        return requestAddCompanyContact;
                    })
                    .toList();
            requestAddCompanyContacts = requestAddCompanyContactRepository.saveAll(requestAddCompanyContacts);
            List<RequestAddCompanyContactResponse> response = requestAddCompanyContacts.stream().map(RequestAddCompanyContactResponse::of).toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }
}
