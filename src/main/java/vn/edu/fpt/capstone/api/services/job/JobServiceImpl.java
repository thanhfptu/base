package vn.edu.fpt.capstone.api.services.job;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.entities.dto.JobDTO;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.*;
import vn.edu.fpt.capstone.api.requests.JobRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service(JobService.SERVICE_NAME)
public class JobServiceImpl implements JobService {

    private final EntityManager entityManager;
    private final JobRepository jobRepository;
    private final MajorRepository majorRepository;
    private final SemesterRepository semesterRepository;
    private final JobMajorRepository jobMajorRepository;
    private final CompanyRepository companyRepository;
    private final RegionRepository regionRepository;
    private final StudentApplyJobRepository studentApplyJobRepository;

    @Override
    public PageResponse<JobDTOResponse> list(Pageable pageable,
                                             String title,
                                             String description,
                                             String requirement,
                                             String benefit,
                                             Integer numberRecruit,
                                             Integer numberApplied,
                                             Long companyId,
                                             String companyName,
                                             Long semesterId,
                                             String semesterName,
                                             Long regionId,
                                             String regionName,
                                             Date publishDate,
                                             Date expiredDate,
                                             String salary,
                                             Boolean isVisible,
                                             Boolean isActive,
                                             Boolean isFeatured,
                                             List<Long> majorIds) {
        StringBuilder selectBuild = new StringBuilder("SELECT ")
                .append("job.id as id, ")
                .append("job.title as title, ")
                .append("company.id as company_id, ")
                .append("company.name as company_name, ")
                .append("job.description as description, ")
                .append("job.requirement as requirement, ")
                .append("job.benefit as benefit, ")
                .append("job.num_of_recruit as num_of_recruit, ")
                .append("job.num_of_applied as num_of_applied, ")
                .append("semester.id as semester_id, ")
                .append("semester.name as semester_name, ")
                .append("region.id as region_id, ")
                .append("region.name as region_name, ")
                .append("job.publish_date as publish_date, ")
                .append("job.expired_date as expired_date, ")
                .append("job.salary as salary, ")
                .append("job.img_url as img_url, ")
                .append("job.is_visible as is_visible, ")
                .append("job.is_active as is_active, ")
                .append("job.is_featured as is_featured ");

        HashMap<String, Object> params = new HashMap<>();

        StringBuilder fromBuild = new StringBuilder("FROM ")
                .append("jobs job ")
                .append("join companies company ")
                .append("on job.company_id = company.id ")
                .append("join semesters semester ")
                .append("on job.semester_id = semester.id ")
                .append("join regions region ")
                .append("on job.region_id = region.id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");

        if (StringUtils.hasText(title)) {
            whereBuild.append("AND job.title LIKE CONCAT('%',:title,'%')");
            params.put("title", title);
        }

        if (StringUtils.hasText(description)) {
            whereBuild.append("AND job.description LIKE CONCAT('%', :description, '%') ");
            params.put("description", description);
        }

        if (Objects.nonNull(numberRecruit)) {
            whereBuild.append("AND job.num_of_recruit = :numberRecruit ");
            params.put("numberRecruit", numberRecruit);
        }

        if (StringUtils.hasText(companyName)) {
            whereBuild.append("AND company.name LIKE CONCAT('%', :companyName, '%') ");
            params.put("companyName", companyName);
        }

        if (Objects.nonNull(companyId)) {
            whereBuild.append("AND job.company_id = :companyId ");
            params.put("companyId", companyId);
        }

        if (StringUtils.hasText(requirement)) {
            whereBuild.append("AND job.requirement LIKE CONCAT('%', :requirement, '%') ");
            params.put("requirement", requirement);
        }

        if (StringUtils.hasText(benefit)) {
            whereBuild.append("AND job.benefit LIKE CONCAT('%', :benefit, '%')' ");
            params.put("benefit", benefit);
        }

        if (Objects.nonNull(numberApplied)) {
            whereBuild.append("AND job.num_of_applied = :numberApplied ");
            params.put("numberApplied", numberApplied);
        }

        if (Objects.nonNull(semesterId)) {
            whereBuild.append("AND job.semester_id = :semesterId ");
            params.put("semesterId", semesterId);
        }

        if (StringUtils.hasText(semesterName)) {
            whereBuild.append("AND semester.name LIKE CONCAT('%', :semesterName, '%') ");
            params.put("semesterName", semesterName);
        }

        if (Objects.nonNull(regionId)) {
            whereBuild.append("AND job.region_Id = :regionId ");
            params.put("regionId", regionId);
        }

        if (StringUtils.hasText(regionName)) {
            whereBuild.append("AND regions.name LIKE CONCAT('%', :regionName, '%') ");
            params.put("regionName", regionName);
        }

        if (StringUtils.hasText(salary)) {
            whereBuild.append("AND job.salary LIKE CONCAT('%', :salary, '%') ");
            params.put("salary", salary);
        }

        if (Objects.nonNull(publishDate)) {
            whereBuild.append("AND job.publish_date = :publishDate ");
            params.put("publishDate", publishDate);
        }

        if (Objects.nonNull(expiredDate)) {
            whereBuild.append("AND job.expired_date = :expiredDate ");
            params.put("expiredDate", expiredDate);
        }

        if (Objects.nonNull(isVisible)) {
            whereBuild.append("AND job.is_visible = :isVisible ");
            params.put("isVisible", isVisible);
        }

        if (Objects.nonNull(isActive)) {
            whereBuild.append("AND job.is_active = :isActive ");
            params.put("isActive", isActive);
        }

        if (Objects.nonNull(isFeatured)) {
            whereBuild.append("AND job.is_featured = :isFeatured ");
            params.put("isFeatured", isFeatured);
        }

        List<Long> jobIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(majorIds)) {
            String queryJobIdsBuild = new StringBuilder("SELECT DISTINCT job_id ")
                    .append("FROM job_majors ")
                    .append("WHERE major_id IN :majorIds")
                    .toString();
            Query queryJobIds = entityManager.createNativeQuery(queryJobIdsBuild)
                    .setParameter("majorIds", majorIds);
            jobIds = queryJobIds.getResultList();
        }

        if (!CollectionUtils.isEmpty(jobIds)) {
            whereBuild.append("AND job.id in :jobIds ");
            params.put("jobIds", jobIds);
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query count = entityManager.createNativeQuery(queryBuild, JobDTO.SQL_RESULT_SET_MAPPING);

        params.forEach(count::setParameter);

        List<JobDTO> total = count.getResultList();

        long totalEntities = total.size();
        long totalPage = (long) Math.ceil((double) totalEntities / pageable.getPageSize());

        queryBuild = queryBuild.concat("ORDER BY job.id DESC LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(queryBuild, JobDTO.SQL_RESULT_SET_MAPPING)
                .setParameter("limit", pageable.getPageSize())
                .setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        params.forEach(query::setParameter);

        List<JobDTO> jobs = query.getResultList();

        List<JobDTOResponse> data = jobs.stream().map(JobDTOResponse::of).toList();

        for (JobDTOResponse JobDTOResponse : data) {
            List<JobMajor> jobMajors = jobMajorRepository.findByJobId(JobDTOResponse.getId());
            List<Long> majorResponseIds = jobMajors.stream().map(JobMajor::getMajorId).toList();
            List<Major> majors = majorRepository.findByIdIn(majorResponseIds);
            List<MajorResponse> responses = majors.stream().map(MajorResponse::of).toList();
            JobDTOResponse.setMajors(responses);
        }

        return PageResponse.success(data, pageable.getPageNumber(), pageable.getPageSize(), totalEntities, totalPage);
    }

    @Override
    public BaseResponse<JobResponse> get(Long jobId) {
        try {
            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy job với id %s!", jobId)));

            Semester semester = semesterRepository.findById(job.getSemesterId()).orElse(null);

            Company company = companyRepository.findById(job.getCompanyId()).orElse(null);

            List<JobMajor> jobMajors = jobMajorRepository.findByJobId(jobId);

            List<Long> majorIds = jobMajors.stream().map(JobMajor::getMajorId).toList();

            List<Major> majors = majorRepository.findByIdIn(majorIds);

            Region region = regionRepository.findById(job.getRegionId()).orElse(null);

            return response(job, company, semester, region, majors);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<JobResponse> create(JobRequest request,
                                            Company company,
                                            List<Major> majors,
                                            Region region,
                                            Long operatorId) {

        Semester semester = semesterRepository.getCurrentSemester();

        Job job = Job.of(request);

        if (!StringUtils.hasText(request.getImgUrl())) {
            job.setImgUrl(company.getImgUrl());
        } else {
            job.setImgUrl(request.getImgUrl());
        }

        job.setSemesterId(semester.getId());
        job.setIsFeatured(Objects.nonNull(request.getIsFeatured()) && Boolean.TRUE.equals(request.getIsFeatured()));
        job.setIsActive(Objects.nonNull(request.getIsActive()) && Boolean.TRUE.equals(request.getIsActive()));
        job.setCreatedAt(DateUtils.now());
        job.setCreatedBy(operatorId);

        job = jobRepository.save(job);

        Long jobId = job.getId();

        List<JobMajor> jobMajors = majors.stream()
                .map(major -> JobMajor.of(jobId, major.getId(), operatorId))
                .toList();

        jobMajorRepository.saveAll(jobMajors);

        return response(job, company, semester, region, majors);
    }

    @Override
    public BaseResponse<JobResponse> update(JobRequest request,
                                            Company company,
                                            List<Major> majors,
                                            Region region,
                                            Long operatorId) {
        try {
            Semester semester;

            if (Objects.isNull(request.getSemesterId())) {
                semester = semesterRepository.getCurrentSemester();
            } else {
                semester = semesterRepository.findById(request.getSemesterId())
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy semester với id %s!", request.getSemesterId())));
            }

            Date publishDate = DateUtils.at(DateUtils.toLocalDate(request.getPublishDate()), LocalTime.MIN);

            Job job = jobRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy job với id %s!", request.getId())));

            job.setTitle(request.getTitle());
            job.setDescription(request.getDescription());
            job.setBenefit(request.getBenefit());
            job.setCompanyId(request.getCompanyId());
            job.setPublishDate(publishDate);
            job.setIsVisible(publishDate.compareTo(DateUtils.now()) <= 0);
            job.setExpiredDate(request.getExpiredDate());
            job.setRegionId(request.getRegionId());
            job.setSemesterId(semester.getId());
            job.setModifiedAt(DateUtils.now());
            job.setModifiedBy(operatorId);
            job.setIsActive(Objects.nonNull(request.getIsActive()) && Boolean.TRUE.equals(request.getIsActive()));
            job.setRequirement(request.getRequirement());
            job.setNumberRecruit(request.getNumberRecruit());
            job.setIsFeatured(Objects.nonNull(request.getIsFeatured()) && Boolean.TRUE.equals(request.getIsFeatured()));

            if (!StringUtils.hasText(request.getImgUrl())) {
                job.setImgUrl(company.getImgUrl());
            } else {
                job.setImgUrl(request.getImgUrl());
            }

            job = jobRepository.save(job);

            List<JobMajor> currentMajors = jobMajorRepository.findByJobId(job.getId());

            List<Long> currentMajorIds = currentMajors.stream().map(JobMajor::getMajorId).toList();

            List<Long> requestMajorIds = request.getMajorIds();

            List<JobMajor> removedMajors = currentMajors.stream()
                    .filter(item -> !requestMajorIds.contains(item.getMajorId()))
                    .toList();

            if (!CollectionUtils.isEmpty(removedMajors)) {
                jobMajorRepository.deleteAll(removedMajors);
            }

            List<JobMajor> newMajors = requestMajorIds.stream()
                    .filter(item -> !currentMajorIds.contains(item))
                    .map(item -> JobMajor.of(request.getId(), item, operatorId))
                    .toList();

            if (!CollectionUtils.isEmpty(newMajors)) {
                jobMajorRepository.saveAll(newMajors);
            }

            return response(job, company, semester, region, majors);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<JobResponse> save(JobRequest request, Long operatorId) {
        try {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy company id %s", request.getCompanyId())));

            Region region = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy region id %s", request.getRegionId())));

            List<Long> majorIds = request.getMajorIds();

            List<Major> majors = majorRepository.findByIdIn(majorIds);

            if (majorIds.size() != majors.size()) {
                throw new IllegalArgumentException("Danh sách Major không hợp lệ!");
            }

            return Objects.isNull(request.getId())
                    ? create(request, company, majors, region, operatorId)
                    : update(request, company, majors, region, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<JobResponse> response(Job job, Company company, Semester semester, Region region, List<Major> majors) {
        SemesterResponse semesterResponse = SemesterResponse.of(semester);

        CompanyResponse companyResponse = CompanyResponse.of(company);

        List<MajorResponse> majorResponses = majors.stream().map(MajorResponse::of).toList();

        RegionResponse regionResponse = RegionResponse.of(region);

        return BaseResponse.success(JobResponse.of(job, companyResponse, semesterResponse, majorResponses, regionResponse));
    }

    @Override
    public BaseResponse<List<JobResponse>> disable(List<Long> jobIds, Long operatorId) {
        try {
            List<Job> jobs = jobRepository.findByIdIn(jobIds);

            if (jobs.size() != jobIds.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            jobs.forEach(job -> job.setIsActive(false));

            jobs = jobRepository.saveAll(jobs);

            List<JobResponse> response = jobs.stream()
                    .map(JobResponse::of)
                    .toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }
}
