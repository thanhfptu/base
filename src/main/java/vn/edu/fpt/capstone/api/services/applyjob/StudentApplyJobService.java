package vn.edu.fpt.capstone.api.services.applyjob;

import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.requests.StudentApplyJobRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.JobResponse;
import vn.edu.fpt.capstone.api.responses.StudentApplyJobResponse;

import java.util.List;

public interface StudentApplyJobService {
    String SERVICE_NAME = "StudentApplyJobService";

    BaseResponse<StudentApplyJobResponse> create(StudentApplyJobRequest request, User operator);

    BaseResponse<List<JobResponse>> listJobsAppliedByCV(Long cvId);

    BaseResponse<String> delete(StudentApplyJobRequest request);
}
