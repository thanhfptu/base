package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.entities.Semester;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class SemesterResponse {

    private Long id;
    private String name;
    private String code;
    private Integer year;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;

    public static SemesterResponse of(Semester semester) {
        return SemesterResponse.builder()
                .id(semester.getId())
                .name(semester.getName())
                .code(semester.getCode())
                .year(semester.getYear())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .isActive(semester.getIsActive())
                .build();
    }

}
