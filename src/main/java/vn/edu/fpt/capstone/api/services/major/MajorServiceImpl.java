package vn.edu.fpt.capstone.api.services.major;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Major;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.MajorResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;
import vn.edu.fpt.capstone.api.repositories.MajorRepository;
import vn.edu.fpt.capstone.api.requests.MajorRequest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(MajorService.SERVICE_NAME)
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    @Override
    public PageResponse<MajorResponse> list(Pageable pageable, String code, String name, Integer level, Long parentId, Boolean enabled) {
        BaseSpecification<Major> specification = new BaseSpecification<>();

        if (StringUtils.hasText(code)) {
            specification.add(new SearchCriteria("code", code, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }

        if (Objects.nonNull(level)) {
            specification.add(new SearchCriteria("level", level, SearchOperation.EQUAL));
        }

        if (Objects.nonNull(parentId)) {
            specification.add(new SearchCriteria("parentId", parentId, SearchOperation.EQUAL));
        }

        if (Objects.nonNull(enabled)) {
            specification.add(new SearchCriteria("enabled", enabled, SearchOperation.EQUAL));
        }

        Page<Major> page = majorRepository.findAll(specification, pageable);

        List<Major> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<MajorResponse> data = content.stream()
                .map(MajorResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<MajorResponse> get(Long id) {
        try {
            Major major = majorRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy ngành học với id %s!", id)));

            Long parentId = major.getParentId();

            Major parentMajor = majorRepository.findById(parentId).orElse(null);

            return response(major, parentMajor);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<MajorResponse> save(MajorRequest request, Long operatorId) {
        try {
            Long parentId = request.getParentId();

            Major parentMajor = null;
            
            if (parentId != 0) {
                parentMajor = majorRepository.findById(parentId)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Major id %s!", parentId)));
            }

            return Objects.isNull(request.getId()) ? create(request, parentMajor, operatorId) : update(request, parentMajor, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<MajorResponse> create(MajorRequest request, Major parentMajor, Long operatorId) {
        Major major = Major.of(request);

        major.setCreatedAt(DateUtils.now());
        major.setCreatedBy(operatorId);
        major.setEnabled(Objects.nonNull(request.getEnabled()) && Boolean.TRUE.equals(request.getEnabled()));
        major = majorRepository.save(major);

        return response(major, parentMajor);
    }

    @Override
    public BaseResponse<MajorResponse> update(MajorRequest request, Major parentMajor, Long operatorId) {
        try {
            Long id = request.getId();

            Major major = majorRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy ngành học với id %s!", id)));

            major.setCode(request.getCode());
            major.setName(request.getName());
            major.setLevel(request.getLevel());
            major.setParentId(request.getParentId());
            major.setEnabled(Objects.nonNull(request.getEnabled()) && Boolean.TRUE.equals(request.getEnabled()));
            major.setModifiedAt(DateUtils.now());
            major.setModifiedBy(operatorId);

            major = majorRepository.save(major);

            return response(major, parentMajor);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<MajorResponse> response(Major major, Major parentMajor) {
        MajorResponse parentMajorResponse = Objects.isNull(parentMajor) ? null : MajorResponse.of(parentMajor);
        return BaseResponse.success(MajorResponse.of(major, parentMajorResponse));
    }
}
