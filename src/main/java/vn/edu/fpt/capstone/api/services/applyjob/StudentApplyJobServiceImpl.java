package vn.edu.fpt.capstone.api.services.applyjob;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Job;
import vn.edu.fpt.capstone.api.entities.StudentApplyJob;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.UserCV;
import vn.edu.fpt.capstone.api.entities.dto.AppliedJobDTO;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.JobRepository;
import vn.edu.fpt.capstone.api.repositories.StudentApplyJobRepository;
import vn.edu.fpt.capstone.api.repositories.UserCVRepository;
import vn.edu.fpt.capstone.api.requests.StudentApplyJobRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.JobResponse;
import vn.edu.fpt.capstone.api.responses.StudentApplyJobResponse;
import vn.edu.fpt.capstone.api.responses.UserCVResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service(StudentApplyJobService.SERVICE_NAME)
public class StudentApplyJobServiceImpl implements StudentApplyJobService {

    private final StudentApplyJobRepository studentApplyJobRepository;

    private final JobRepository jobRepository;

    private final UserCVRepository userCVRepository;

    private final Integer AMOUNT_LIMIT = 2;

    @Override
    public BaseResponse<StudentApplyJobResponse> create(StudentApplyJobRequest request, User operator) {
        try {
            StudentApplyJob stored = studentApplyJobRepository.findByJobIdAndCvId(request.getJobId(), request.getCvId());

            if (Objects.nonNull(stored)) {
                throw new IllegalArgumentException("CV của bạn đã ứng tuyển công việc này!");
            }

            List<StudentApplyJob> countAppliedJob = studentApplyJobRepository.findByCreatedBy(operator.getId());

            StudentApplyJob studentApplyJob = studentApplyJobRepository.findByJobIdAndCreatedBy(request.getJobId(), operator.getId());
            if (countAppliedJob.size() >= AMOUNT_LIMIT) {
                if (Objects.isNull(studentApplyJob)) {
                    throw new IllegalArgumentException(String.format("Bạn đã ứng tuyển %s công việc!",AMOUNT_LIMIT));
                }
            }

            if (Objects.isNull(studentApplyJob)) {
                studentApplyJob = StudentApplyJob.of(request, operator.getId());
            } else {
                Long cvId = studentApplyJob.getCvId();
                UserCV userCV = userCVRepository.findById(cvId)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cv với id %s", cvId)));
                if (CVStatus.APPROVED.equals(userCV.getStatus())) {
                    throw new IllegalArgumentException("CV của bạn đã được duyệt, không thể ứng tuyển lại!");
                }
                studentApplyJob.setCvId(request.getCvId());
                studentApplyJob.setModifiedAt(DateUtils.now());
                studentApplyJob.setModifiedBy(operator.getId());
            }

            studentApplyJob = studentApplyJobRepository.save(studentApplyJob);

            Long jobId = request.getJobId();

            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Job với id %s", jobId)));

            Long userCVId = request.getCvId();

            UserCV userCV = userCVRepository.findById(userCVId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy CV với id %s", userCVId)));

            job.setNumberApplied(job.getNumberApplied() + 1);

            job = jobRepository.save(job);

            StudentApplyJobResponse response = StudentApplyJobResponse.of(studentApplyJob, JobResponse.of(job), UserCVResponse.of(userCV));

            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }

    }

    @Override
    public BaseResponse<List<JobResponse>> listJobsAppliedByCV(Long cvId) {
        BaseSpecification<StudentApplyJob> specification = new BaseSpecification<>();
        specification.add(new SearchCriteria("cvId", cvId, SearchOperation.EQUAL));
        List<StudentApplyJob> applyJobs = studentApplyJobRepository.findAll(specification);
        List<Long> jobIds = applyJobs.stream().map(StudentApplyJob::getJobId).toList();
        List<Job> jobs = jobRepository.findByIdIn(jobIds);
        List<JobResponse> response = jobs.stream().map(JobResponse::of).toList();
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<String> delete(StudentApplyJobRequest request) {
        StudentApplyJob data = studentApplyJobRepository.findByJobIdAndCvId(request.getJobId(), request.getCvId());
        String msg = "";
        if (Objects.isNull(data)) {
            throw new IllegalArgumentException("Không có dữ liệu ứng tuyển công việc");
        }
        try {
            studentApplyJobRepository.delete(data);
            return BaseResponse.success("Hủy ứng tuyển công việc thành công!");
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }
    }
}
