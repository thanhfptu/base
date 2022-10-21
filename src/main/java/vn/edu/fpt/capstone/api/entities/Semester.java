package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import org.checkerframework.checker.units.qual.N;
import vn.edu.fpt.capstone.api.requests.SemesterRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Semester.TABLE_NAME)
public class Semester extends BaseEntity {

    public static final String TABLE_NAME = "semesters";

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "year")
    private Integer year;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    public static Semester of(SemesterRequest semester) {
        return Semester.builder()
                .name(semester.getName().strip())
                .code(semester.getCode().strip())
                .year(semester.getYear())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .isActive(semester.getIsActive())
                .build();
    }

    public static Semester of(SemesterRequest semester, Long createdBy) {
        Semester build = Semester.of(semester);
        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }
        return build;
    }

}
