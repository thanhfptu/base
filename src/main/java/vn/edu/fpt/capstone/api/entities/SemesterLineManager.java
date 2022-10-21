package vn.edu.fpt.capstone.api.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = SemesterLineManager.TABLE_NAME)
public class SemesterLineManager extends BaseEntity {

    public static final String TABLE_NAME = "semester_line_managers";

    @Column(name = "line_manager_id")
    private Long lineManagerId;

    @Column(name = "semester_id")
    private Long semesterId;

    @Column(name = "otp")
    private String otp;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "issued_at")
    private Date issuedAt;

    public static SemesterLineManager of(Long lineManagerId,
                                         Long semesterId,
                                         String otp,
                                         Boolean enabled,
                                         Date issuedAt) {
        return SemesterLineManager.builder()
                .lineManagerId(lineManagerId)
                .semesterId(semesterId)
                .otp(otp)
                .enabled(enabled)
                .issuedAt(issuedAt)
                .build();
    }

}
