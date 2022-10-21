package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.StudentApplyJobRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = StudentApplyJob.TABLE_NAME)
public class StudentApplyJob extends BaseEntity {

    public static final String TABLE_NAME = "student_apply_job";

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "cv_id")
    private Long cvId;

    public static StudentApplyJob of(StudentApplyJobRequest request) {
        return StudentApplyJob.builder()
                .jobId(request.getJobId())
                .cvId(request.getCvId())
                .build();
    }

    public static StudentApplyJob of(StudentApplyJobRequest request, Long operatorId) {
        StudentApplyJob build = StudentApplyJob.of(request);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(operatorId);
        return build;
    }
}
