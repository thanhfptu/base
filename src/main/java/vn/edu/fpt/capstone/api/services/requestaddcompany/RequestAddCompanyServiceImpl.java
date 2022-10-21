package vn.edu.fpt.capstone.api.services.requestaddcompany;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Company;
import vn.edu.fpt.capstone.api.entities.RequestAddCompany;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.CompanyRepository;
import vn.edu.fpt.capstone.api.repositories.RequestAddCompanyRepository;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;
import vn.edu.fpt.capstone.api.utils.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(RequestAddCompanyService.SERVICE_NAME)
public class RequestAddCompanyServiceImpl implements RequestAddCompanyService{

    private final RequestAddCompanyRepository requestAddCompanyRepository;

    private final CompanyRepository companyRepository;
    
    @Override
    public PageResponse<RequestAddCompanyResponse> list(Pageable pageable, String taxCode, String name, Long createdBy) {
        BaseSpecification<RequestAddCompany> specification = new BaseSpecification<>();
        if (StringUtils.hasText(taxCode)) {
            specification.add(new SearchCriteria("taxCode", taxCode, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.LIKE));
        }
        if (Objects.nonNull(createdBy)) {
            specification.add(new SearchCriteria("createdBy", createdBy, SearchOperation.EQUAL));
        }
        Page<RequestAddCompany> page = requestAddCompanyRepository.findAll(specification, pageable);

        List<RequestAddCompany> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<RequestAddCompanyResponse> data = content.stream()
                .map(RequestAddCompanyResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<RequestAddCompanyResponse> get(Long id) {
        try {
            RequestAddCompany requestAddCompany = requestAddCompanyRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy request add company với id %s", id)));
            return BaseResponse.success(RequestAddCompanyResponse.of(requestAddCompany));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RequestAddCompanyResponse> create(RequestAddCompanyRequest request, Long operatorId) {
        try {
            RequestAddCompany requestAddCompany = RequestAddCompany.of(request, operatorId);
            Company company = companyRepository.findByTaxCode(request.getTaxCode())
                    .orElse(null);
            if (Objects.nonNull(company)) {
                throw new IllegalArgumentException(String.format("Công ty đã tồn tại với tax code %s", request.getTaxCode()));
            }
            requestAddCompany.setStatus(OJTInfoStatus.UNCHECKED);

            String offerFileName = String.format("%s/%s.docx",
                    Paths.get(System.getProperty("java.io.tmpdir")).normalize(),
                    FileUtils.generateFileName(request.getOfferFile().getOriginalFilename(), ".docx", operatorId));
            FileUtils.convertToFile(request.getOfferFile(), offerFileName);
            String offerUrl = FileUtils.uploadFile(offerFileName,"offer");

            String mouFileName = String.format("%s/%s.docx",
                    Paths.get(System.getProperty("java.io.tmpdir")).normalize(),
                    FileUtils.generateFileName(request.getMouFile().getOriginalFilename(), ".docx", operatorId));
            FileUtils.convertToFile(request.getOfferFile(), mouFileName);
            String mouUrl = FileUtils.uploadFile(mouFileName, "mou");
            if(!StringUtils.hasText(offerUrl) || !StringUtils.hasText(mouUrl)){
                throw new IllegalArgumentException("Upload file thất bại, thử lại sau.");
            }
            requestAddCompany.setMouUrl(mouUrl);
            requestAddCompany.setOfferUrl(offerUrl);
            requestAddCompany = requestAddCompanyRepository.save(requestAddCompany);
            return BaseResponse.success(RequestAddCompanyResponse.of(requestAddCompany));
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    @Override
    public BaseResponse<RequestAddCompanyResponse> update(RequestAddCompanyRequest request, Long operatorId) {
        try {
            Long id = request.getId();
            RequestAddCompany requestAddCompany = requestAddCompanyRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy request add company với id %s", id)));
            requestAddCompany.setModifiedAt(DateUtils.now());
            requestAddCompany.setModifiedBy(operatorId);
            requestAddCompany.setName(request.getName());
            if(Objects.nonNull(request.getOfferFile())){
                String offerFileName = String.format("%s/%s.pdf",
                        Paths.get(System.getProperty("java.io.tmpdir")).normalize(),
                        FileUtils.generateFileName(request.getOfferFile().getOriginalFilename(), ".docx", operatorId));
                FileUtils.convertToFile(request.getOfferFile(), offerFileName);
                String offerUrl = FileUtils.uploadFile(request.getOfferFile().getOriginalFilename(),"offer");
                if(!StringUtils.hasText(offerUrl)){
                    throw new IllegalArgumentException("Upload file offer thất bại, thử lại sau.");
                }
                requestAddCompany.setOfferUrl(offerUrl);
            }

            if(Objects.nonNull(request.getMouFile())){
                String mouFileName = String.format("%s/%s.pdf",
                        Paths.get(System.getProperty("java.io.tmpdir")).normalize(),
                        FileUtils.generateFileName(request.getMouFile().getOriginalFilename(), ".docx", operatorId));
                FileUtils.convertToFile(request.getOfferFile(), mouFileName);
                String mouUrl = FileUtils.uploadFile(request.getMouFile().getOriginalFilename(), "mou");
                if(!StringUtils.hasText(mouUrl)){
                    throw new IllegalArgumentException("Upload file mou thất bại, thử lại sau.");
                }
                requestAddCompany.setMouUrl(mouUrl);
            }

            requestAddCompany = requestAddCompanyRepository.save(requestAddCompany);
            return BaseResponse.success(RequestAddCompanyResponse.of(requestAddCompany));
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RequestAddCompanyResponse> save(RequestAddCompanyRequest request, Long operatorId) {
        return Objects.isNull(request.getId()) ? create(request, operatorId) : update(request, operatorId);
    }

    @Override
    public BaseResponse<List<RequestAddCompanyResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId) {
        try {
            List<RequestAddCompany> requestAddCompanies = requestAddCompanyRepository.findByIdIn(ids);

            if (requestAddCompanies.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            requestAddCompanies = requestAddCompanies.stream()
                    .map(requestAddCompany -> {
                        requestAddCompany.setStatus(OJTInfoStatus.of(status));
                        requestAddCompany.setModifiedBy(operatorId);
                        requestAddCompany.setModifiedAt(DateUtils.now());
                        return requestAddCompany;
                    })
                    .toList();
            requestAddCompanies = requestAddCompanyRepository.saveAll(requestAddCompanies);
            List<RequestAddCompanyResponse> response = requestAddCompanies.stream().map(RequestAddCompanyResponse::of).toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }
}
