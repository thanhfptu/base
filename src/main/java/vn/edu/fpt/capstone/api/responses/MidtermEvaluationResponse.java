package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.constants.EvaluationComment;
import vn.edu.fpt.capstone.api.entities.MidtermEvaluation;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class MidtermEvaluationResponse {
    private Long id;

    private Date startDate;

    private Date endDate;

    private Integer duration;

    private String staffCode;

    private String division;

    private EvaluationComment comment;

    private String note;

    public static MidtermEvaluationResponse of(MidtermEvaluation midtermEvaluation) {
        return MidtermEvaluationResponse.builder()
                .id(midtermEvaluation.getId())
                .startDate(midtermEvaluation.getStartDate())
                .endDate(midtermEvaluation.getEndDate())
                .duration(midtermEvaluation.getDuration())
                .division(midtermEvaluation.getDivision())
                .staffCode(midtermEvaluation.getStaffCode())
                .comment(midtermEvaluation.getComment())
                .note(midtermEvaluation.getNote())
                .build();
    }
}
