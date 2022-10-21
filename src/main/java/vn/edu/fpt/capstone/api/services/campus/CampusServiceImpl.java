package vn.edu.fpt.capstone.api.services.campus;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.CampusStatus;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Campus;
import vn.edu.fpt.capstone.api.entities.Region;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.CampusRepository;
import vn.edu.fpt.capstone.api.repositories.RegionRepository;
import vn.edu.fpt.capstone.api.requests.CampusRequest;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CampusResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RegionResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service(CampusService.SERVICE_NAME)
public class CampusServiceImpl implements CampusService {

    private final CampusRepository campusRepository;

    private final RegionRepository regionRepository;

    @Override
    public PageResponse<CampusResponse> list(Pageable pageable,
                                             String name,
                                             String address,
                                             Long regionId,
                                             String phoneNumber,
                                             String email,
                                             Integer status) {
        BaseSpecification<Campus> specification = new BaseSpecification<>();

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(address)) {
            specification.add(new SearchCriteria("address", address, SearchOperation.MATCH));
        }

        if (Objects.nonNull(regionId)) {
            specification.add(new SearchCriteria("regionId", regionId, SearchOperation.EQUAL));
        }

        if (StringUtils.hasText(phoneNumber)) {
            specification.add(new SearchCriteria("phoneNumber", phoneNumber, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(email)) {
            specification.add(new SearchCriteria("email", email, SearchOperation.MATCH));
        }

        if (Objects.nonNull(status)) {
            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
        }

        Page<Campus> page = campusRepository.findAll(specification, pageable);

        List<Campus> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<CampusResponse> data = content.stream()
                .map(CampusResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<CampusResponse> get(Long id) {
        try {
            Campus campus = campusRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cơ sở với id %s", id)));

            Region region = Objects.isNull(campus.getRegionId())
                    ? null
                    : regionRepository.findById(campus.getRegionId()).orElse(null);

            return response(campus, region);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    @Override
    public BaseResponse<CampusResponse> create(CampusRequest request, Region region, Long operatorId) {
        Campus campus = Campus.of(request);
        if (StringUtils.hasText(request.getAddress())) {
            campus.setAddress(request.getAddress().strip());
        }
        if (StringUtils.hasText(request.getEmail())) {
            String email = request.getEmail().strip();
            Optional<Campus> campusOpt = campusRepository.findByEmail(email);
            if (campusOpt.isPresent()) {
                return BaseResponse.error(String.format("Email %s đã tồn tại!", email));
            }
            campus.setEmail(email);
        }
        if (StringUtils.hasText(request.getPhoneNumber())) {
            String phoneNumber = request.getPhoneNumber().strip();
            Optional<Campus> campusOpt = campusRepository.findByPhoneNumber(phoneNumber);
            if (campusOpt.isPresent()) {
                return BaseResponse.error(String.format("Số điện thoại %s đã tồn tại!", phoneNumber));
            }
            campus.setPhoneNumber(phoneNumber);
        }
        campus.setCreatedAt(DateUtils.now());
        campus.setCreatedBy(operatorId);

        campus = campusRepository.save(campus);

        return response(campus, region);
    }

    @Override
    public BaseResponse<CampusResponse> update(CampusRequest request, Region region, Long operatorId) {
        try {
            Long id = request.getId();
            Campus campus = campusRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cơ sở với id %s", id)));

            String email = StringUtils.hasText(request.getEmail()) ? request.getEmail().strip() : "";
            campus.setEmail(email);

            String phoneNumber = StringUtils.hasText(request.getPhoneNumber()) ? request.getPhoneNumber().strip() : "";
            campus.setPhoneNumber(phoneNumber);

            campus.setName(request.getName().strip());
            campus.setAddress(StringUtils.hasText(request.getAddress()) ? request.getAddress().strip() : "");
            campus.setRegionId(request.getRegionId());
            campus.setStatus(CampusStatus.of(request.getStatus()));
            campus.setModifiedBy(operatorId);
            campus.setModifiedAt(DateUtils.now());

            campus = campusRepository.save(campus);

            return response(campus, region);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CampusResponse> save(CampusRequest request, Long operatorId) {
        try {
            if (Objects.isNull(request.getRegionId())) {
                throw new IllegalArgumentException("Không được để trống regionId");
            }

            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Region id %s!", request.getRegionId())));

            return Objects.isNull(request.getId()) ? create(request, region, operatorId) : update(request, region, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CampusResponse> response(Campus campus, Region region) {
        RegionResponse regionResponse = RegionResponse.of(region);

        return BaseResponse.success(CampusResponse.of(campus, regionResponse));
    }

    @Override
    public BaseResponse<List<CampusResponse>> disable(IdsRequest request, Long operatorId) {
        try {
            List<Long> ids = request.getIds();

            List<Campus> campuses = campusRepository.findByIdIn(ids);

            if (campuses.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            campuses = campuses.stream()
                    .map((campus) -> {
                        campus.setStatus(CampusStatus.INACTIVE);
                        campus.setModifiedBy(operatorId);
                        campus.setModifiedAt(DateUtils.now());
                        return campus;
                    })
                    .toList();

            campuses = campusRepository.saveAll(campuses);

            return BaseResponse.success(campuses.stream().map(CampusResponse::of).toList());
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
