package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = JobMajor.TABLE_NAME)
public class JobMajor extends BaseEntity{

    public static final String TABLE_NAME = "job_majors";

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "major_id")
    private Long majorId;

    public static JobMajor of(Long jobId, Long majorId) {
        return JobMajor.builder()
                .jobId(jobId)
                .majorId(majorId)
                .build();
    }

    public static JobMajor of(Long jobId, Long majorId, Long operatorId) {
        JobMajor build = JobMajor.of(jobId, majorId);

        if (Objects.nonNull(operatorId)) {
            build.setCreatedAt(DateUtils.now());
            build.setCreatedBy(operatorId);
        }

        return build;
    }
}
