package vn.edu.fpt.capstone.api.services.semester;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.SemesterResponse;
import vn.edu.fpt.capstone.api.entities.Semester;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.SemesterRepository;
import vn.edu.fpt.capstone.api.requests.SemesterRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(SemesterService.SERVICE_NAME)
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Override
    public PageResponse<SemesterResponse> list(Pageable pageable,
                                               String name,
                                               String code,
                                               Integer year,
                                               Date startDate,
                                               Date endDate,
                                               Boolean isActive) {
        BaseSpecification<Semester> specification = new BaseSpecification<>();

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(code)) {
            specification.add(new SearchCriteria("code", code, SearchOperation.MATCH));
        }

        if (Objects.nonNull(year)) {
            specification.add(new SearchCriteria("year", year, SearchOperation.EQUAL));
        }

        if (Objects.nonNull(startDate)) {
            specification.add(new SearchCriteria("startDate", startDate, SearchOperation.GREATER_THAN_EQUAL));
        }

        if (Objects.nonNull(endDate)) {
            specification.add(new SearchCriteria("endDate", endDate, SearchOperation.LESS_THAN_EQUAL));
        }

        if (Objects.nonNull(isActive)) {
            specification.add(new SearchCriteria("isActive", isActive, SearchOperation.EQUAL));
        }

        Page<Semester> page = semesterRepository.findAll(specification, pageable);

        List<Semester> content = page.getContent();

        if (CollectionUtils.isEmpty(content)) content = Collections.emptyList();

        List<SemesterResponse> data = content.stream()
                .map(SemesterResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<SemesterResponse> get(Long id) {
        try {
            Semester semester = semesterRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy kỳ học với id %s!", id)));

            return BaseResponse.success(SemesterResponse.of(semester));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<SemesterResponse> save(SemesterRequest request, Long operatorId) {
        return Objects.isNull(request.getId()) ? create(request, operatorId) : update(request, operatorId);
    }

    @Override
    public BaseResponse<SemesterResponse> create(SemesterRequest request, Long operatorId) {
        Semester semester = semesterRepository.save(Semester.of(request, operatorId));
        return BaseResponse.success(SemesterResponse.of(semester));
    }

    @Override
    public BaseResponse<SemesterResponse> update(SemesterRequest request, Long operatorId) {
        try {
            Long semesterId = request.getId();
            Semester semester = semesterRepository.findById(semesterId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Semester với id %s!", semesterId)));

            semester.setName(request.getName().strip());
            semester.setCode(request.getCode().strip());
            semester.setYear(request.getYear());
            semester.setStartDate(request.getStartDate());
            semester.setEndDate(request.getEndDate());
            semester.setIsActive(request.getIsActive());
            semester.setModifiedBy(operatorId);
            semester.setModifiedAt(DateUtils.now());

            semester = semesterRepository.save(semester);

            return BaseResponse.success(SemesterResponse.of(semester));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<List<SemesterResponse>> disable(IdsRequest request, Long operatorId) {
        try {
            List<Long> ids = request.getIds();
            List<Semester> semesters = semesterRepository.findByIdIn(ids);
            if (semesters.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lê!");
            }
            semesters = semesters.stream()
                    .map((semester) -> {
                        semester.setIsActive(false);
                        semester.setModifiedBy(operatorId);
                        semester.setModifiedAt(DateUtils.now());
                        return semester;
                    }).toList();
            semesters = semesterRepository.saveAll(semesters);
            return BaseResponse.success(semesters.stream().map(SemesterResponse::of).toList());
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
