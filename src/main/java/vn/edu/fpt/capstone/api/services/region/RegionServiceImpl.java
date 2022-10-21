package vn.edu.fpt.capstone.api.services.region;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.entities.Region;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.RegionRepository;
import vn.edu.fpt.capstone.api.requests.RegionRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RegionResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(RegionService.SERVICE_NAME)
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public PageResponse<RegionResponse> list(Pageable pageable, Integer level, String name, Long parentId) {

        BaseSpecification<Region> specification = new BaseSpecification<>();

        if (Objects.nonNull(level)) {
            specification.add(new SearchCriteria("level", level, SearchOperation.EQUAL));
        }

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }

        if (Objects.nonNull(parentId)) {
            specification.add(new SearchCriteria("parentId", parentId, SearchOperation.EQUAL));
        }

        Page<Region> page = regionRepository.findAll(specification, pageable);

        List<Region> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<RegionResponse> data = content.stream()
                .map(RegionResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<RegionResponse> get(Long id) {
        try {
            Region region = regionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy region với id %s", id)));

            Region parentRegion = regionRepository.findById(region.getParentId()).orElse(null);

            return response(region, parentRegion);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RegionResponse> create(RegionRequest request, Region parentRegion, Long operatorId) {
        Region region = Region.of(request, operatorId);

        region = regionRepository.save(region);
        return response(region, parentRegion);
    }

    @Override
    public BaseResponse<RegionResponse> update(RegionRequest request, Region parentRegion, Long operatorId) {
        try {
            Region region = regionRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy region với id %s", request.getId())));

            region.setLevel(request.getLevel());
            region.setName(request.getName());
            region.setParentId(request.getParentId());
            region.setModifiedAt(DateUtils.now());
            region.setModifiedBy(operatorId);

            region = regionRepository.save(region);

            return response(region, parentRegion);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RegionResponse> save(RegionRequest request, Long operatorId) {
        try {
            Long parentId = request.getParentId();

            Region parentRegion = null;

            if (parentId != 0) {
                parentRegion = regionRepository.findById(parentId)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy region với id %s", parentId)));
            }

            return Objects.isNull(request.getId())
                    ? create(request, parentRegion, operatorId)
                    : update(request, parentRegion, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<RegionResponse> response(Region region, Region parentRegion) {
        return BaseResponse.success(RegionResponse.of(region, parentRegion));
    }
}
