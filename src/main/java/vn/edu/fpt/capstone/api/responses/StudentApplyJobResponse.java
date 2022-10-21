package vn.edu.fpt.capstone.api.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.entities.StudentApplyJob;

@Builder
@Setter
@Getter
public class StudentApplyJobResponse {
    private Long id;
    private JobResponse job;
    private UserCVResponse userCV;

    public static StudentApplyJobResponse of(StudentApplyJob studentApplyJob) {
        return StudentApplyJobResponse.builder()
                .id(studentApplyJob.getId())
                .job(JobResponse.builder().id(studentApplyJob.getJobId()).build())
                .userCV(UserCVResponse.builder().id(studentApplyJob.getCvId()).build())
                .build();
    }

    public static StudentApplyJobResponse of(StudentApplyJob studentApplyJob, JobResponse job, UserCVResponse userCV) {
        StudentApplyJobResponse build = StudentApplyJobResponse.of(studentApplyJob);
        build.setJob(job);
        build.setUserCV(userCV);
        return build;
    }
}
