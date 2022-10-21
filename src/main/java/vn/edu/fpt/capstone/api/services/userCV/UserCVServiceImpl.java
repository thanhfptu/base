package vn.edu.fpt.capstone.api.services.userCV;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.capstone.api.configs.kafka.producers.UploadCVProducerConfig;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.entities.CVPreview;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.UserCV;
import vn.edu.fpt.capstone.api.entities.dto.AppliedJobDTO;
import vn.edu.fpt.capstone.api.entities.dto.ManageCVDTO;
import vn.edu.fpt.capstone.api.messages.UploadCVMessage;
import vn.edu.fpt.capstone.api.repositories.CVPreviewRepository;
import vn.edu.fpt.capstone.api.repositories.UserCVRepository;
import vn.edu.fpt.capstone.api.requests.UserCVRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CVPreviewResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.UserCVResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;
import vn.edu.fpt.capstone.api.utils.FileUtils;
import vn.edu.fpt.capstone.api.utils.JSONUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static vn.edu.fpt.capstone.api.entities.dto.ManageCVDTO.SQL_RESULT_SET_MAPPING;

@Slf4j
@Service(UserCVService.SERVICE_NAME)
public class UserCVServiceImpl implements UserCVService {

    private final UserCVRepository userCVRepository;

    private final CVPreviewRepository cvPreviewRepository;

    private final EntityManager entityManager;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String DEFAULT_IMG_URL = "https://res.cloudinary.com/fpt-capstone/image/upload/v1660562440/fptu-capstone/zhrlnpra4zbygfh1dm3y.jpg";

    @Autowired
    public UserCVServiceImpl(CVPreviewRepository cvPreviewRepository, UserCVRepository userCVRepository, EntityManager entityManager,
                             @Qualifier(UploadCVProducerConfig.KAFKA_TEMPLATE) KafkaTemplate<String, String> kafkaTemplate) {
        this.userCVRepository = userCVRepository;
        this.entityManager = entityManager;
        this.cvPreviewRepository = cvPreviewRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PageResponse<ManageCVDTO> list(Pageable pageable,
                                          Long companyId,
                                          Long jobId,
                                          String email,
                                          Integer status) {

        StringBuilder selectBuild = new StringBuilder("SELECT ")
                .append("apply.id as id, ")
                .append("company.id as company_id, ")
                .append("company.name as company_name, ")
                .append("job.id as job_id, ")
                .append("job.title as job_title, ")
                .append("cv.id as cv_id, ")
                .append("cv.url as url, ")
                .append("student.edu_email as edu_email, ")
                .append("cv.status as status ");

        StringBuilder fromBuild = new StringBuilder("FROM ")
                .append("student_apply_job apply ")
                .append("join jobs job on apply.job_id = job.id ")
                .append("join user_cv cv on apply.cv_id = cv.id ")
                .append("join companies company on job.company_id = company.id ")
                .append("join users student on cv.student_id = student.id ");

        Map<String, Object> params = new HashMap<>();

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");

        if (Objects.nonNull(companyId)) {
            whereBuild.append("AND company.id = :companyId ");
            params.put("companyId", companyId);
        }

        if (Objects.nonNull(jobId)) {
            whereBuild.append("AND job.id = :jobId ");
            params.put("jobId", jobId);
        }

        if (StringUtils.hasText(email)) {
            whereBuild.append("AND student.edu_email LIKE CONCAT('%', :email, '%') ");
            params.put("email", email);
        }

        if (Objects.nonNull(status)) {
            whereBuild.append("AND cv.status = :status ");
            params.put("status", status);
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query count = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);

        params.forEach(count::setParameter);

        List<ManageCVDTO> total = count.getResultList();
        long totalEntities = total.size();
        long totalPage = (long) Math.ceil((double) totalEntities / pageable.getPageSize());

        queryBuild = queryBuild.concat("ORDER BY apply.id desc LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(queryBuild, ManageCVDTO.SQL_RESULT_SET_MAPPING)
                .setParameter("offset", pageable.getPageNumber() * pageable.getPageSize())
                .setParameter("limit", pageable.getPageSize());

        params.forEach(query::setParameter);

        List<ManageCVDTO> data = query.getResultList();

        return PageResponse.success(data, pageable.getPageNumber(), pageable.getPageSize(), totalEntities, totalPage);
    }

    @Override
    public BaseResponse<UserCVResponse> get(Long id) {
        try {
            UserCV userCV = userCVRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy CV với id %s!", id)));

            List<CVPreview> cvPreview = cvPreviewRepository.findByCvId(userCV.getId());

            if (CollectionUtils.isEmpty(cvPreview))
                throw new IllegalArgumentException(String.format("Không tìm thấy cv preview với cv_id %s!", userCV.getId()));

            StringBuilder selectBuild = new StringBuilder("SELECT ")
                    .append("job.id as id, ")
                    .append("job.title as title ");

            StringBuilder fromBuild = new StringBuilder("FROM ")
                    .append("student_apply_job appliedJob ")
                    .append("JOIN jobs job on appliedJob.job_id = job.id ");

            StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ")
                    .append("AND appliedJob.cv_id = :cvId");

            String queryBuild = new StringBuilder()
                    .append(selectBuild)
                    .append(fromBuild)
                    .append(whereBuild)
                    .toString();

            Query query = entityManager.createNativeQuery(queryBuild, AppliedJobDTO.SQL_RESULT_SET_MAPPING)
                    .setParameter("cvId", id);
            List<AppliedJobDTO> appliedJobs = query.getResultList();
            return response(userCV, cvPreview, appliedJobs);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<List<UserCVResponse>> getByStudentId(Long studentId) {
        try {

            List<UserCV> userCVS = userCVRepository.findByStudentId(studentId);

            if (CollectionUtils.isEmpty(userCVS)) {
                return BaseResponse.success(null);
            }

            List<UserCVResponse> responseList = userCVS.stream()
                    .map(UserCVResponse::of)
                    .toList();

            for (UserCVResponse response : responseList) {
                List<CVPreview> cvPreview = cvPreviewRepository.findByCvId(response.getId());
                List<CVPreviewResponse> cvPreviewResponse = cvPreview.stream()
                        .map(CVPreviewResponse::of)
                        .toList();
                response.setCvPreview(cvPreviewResponse);
                StringBuilder selectBuild = new StringBuilder("SELECT ")
                        .append("job.id as id, ")
                        .append("job.title as title ");

                StringBuilder fromBuild = new StringBuilder("FROM ")
                        .append("student_apply_job appliedJob ")
                        .append("JOIN jobs job on appliedJob.job_id = job.id ");

                StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ")
                        .append("AND appliedJob.cv_id = :cvId");

                String queryBuild = new StringBuilder()
                        .append(selectBuild)
                        .append(fromBuild)
                        .append(whereBuild)
                        .toString();

                Query query = entityManager.createNativeQuery(queryBuild, AppliedJobDTO.SQL_RESULT_SET_MAPPING)
                        .setParameter("cvId", response.getId());
                List<AppliedJobDTO> appliedJobs = query.getResultList();
                response.setAppliedJobs(appliedJobs);
            }

            return BaseResponse.success(responseList);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserCVResponse> response(UserCV userCV, List<CVPreview> cvPreview, List<AppliedJobDTO> appliedJobs) {
        List<CVPreviewResponse> cvPreviewResponses = CollectionUtils.isEmpty(cvPreview)
                ? null
                : cvPreview.stream().map(CVPreviewResponse::of).toList();

        return BaseResponse.success(UserCVResponse.of(userCV, cvPreviewResponses, appliedJobs));
    }

    @Override
    public BaseResponse<UserCVResponse> upload(MultipartFile cv, User operator) {
        try {
            if (!FileUtils.isPdf(cv) || FileUtils.isEmptyName(cv)) {
                throw new IOException(String.format("Tên file không hợp lệ!"));
            }

            UserCV userCV = new UserCV();

            String generatedFileName = String.format("%s.pdf", FileUtils.generateFileName(cv.getOriginalFilename(), operator.getId()));

            FileUtils.convertToFile(cv, generatedFileName);

            userCV.setFileName(generatedFileName);
            userCV.setUrl(FileUtils.uploadFile(generatedFileName, "CV"));
            userCV.setThumbnailUrl(DEFAULT_IMG_URL);
            userCV.setStudentId(operator.getId());
            userCV.setEnabled(true);
            userCV.setStatus(CVStatus.UNCHECKED);
            userCV.setCreatedBy(operator.getId());
            userCV.setCreatedAt(DateUtils.now());

            userCV = userCVRepository.save(userCV);

            UserCVResponse response = UserCVResponse.of(userCV);

            kafkaTemplate.send(UploadCVProducerConfig.TOPIC, JSONUtils.toJSON(UploadCVMessage.of(userCV)));

            return BaseResponse.success(response);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    @Override
    public BaseResponse<UserCVResponse> update(MultipartFile cv, User operator, Long id) {
        try {
            if (!FileUtils.isPdf(cv) || FileUtils.isEmptyName(cv)) {
                throw new IOException(String.format("Tên file không hợp lệ!"));
            }

            UserCV userCV = userCVRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy CV với id %s!, id")));

            String generatedFileName = String.format("%s.pdf", FileUtils.generateFileName(cv.getOriginalFilename(), operator.getId()));

            FileUtils.convertToFile(cv, generatedFileName);

            userCV.setFileName(generatedFileName);
            userCV.setUrl(FileUtils.uploadFile(generatedFileName, "CV"));
            userCV.setThumbnailUrl(DEFAULT_IMG_URL);
            userCV.setStatus(CVStatus.FAILED.equals(userCV.getStatus()) ? CVStatus.EDITED : CVStatus.UNCHECKED);
            userCV.setModifiedBy(operator.getId());
            userCV.setModifiedAt(DateUtils.now());

            userCV = userCVRepository.save(userCV);
            UserCVResponse response = UserCVResponse.of(userCV);

            kafkaTemplate.send(UploadCVProducerConfig.TOPIC, JSONUtils.toJSON(UploadCVMessage.of(userCV)));

            return BaseResponse.success(response);
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<List<UserCVResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId) {
        try {
            List<UserCV> userCVs = userCVRepository.findByIdIn(ids);

            if (userCVs.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }

            userCVs = userCVs.stream()
                    .map(userCV -> {
                        userCV.setStatus(CVStatus.of(status));
                        userCV.setModifiedBy(operatorId);
                        userCV.setModifiedAt(DateUtils.now());
                        return userCV;
                    })
                    .toList();
            userCVs = userCVRepository.saveAll(userCVs);
            List<UserCVResponse> response = userCVs.stream().map(UserCVResponse::of).toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserCVResponse> updateApprove(UserCVRequest request, Long operatorId) {
        try {
            UserCV userCV = userCVRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy CV với id %s!, id")));

            userCV.setStatus(CVStatus.of(request.getStatus()));
            userCV.setModifiedBy(operatorId);
            userCV.setModifiedAt(DateUtils.now());
            userCV = userCVRepository.save(userCV);
            return BaseResponse.success(UserCVResponse.of(userCV));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
