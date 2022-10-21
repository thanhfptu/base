package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.entities.FinalEvaluation;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class FinalEvaluationResponse {
    private Long id;

    private Date startDate;

    private Date endDate;

    private Integer duration;

    private String staffCode;

    private String division;

    private String comment;

    private String allowance;

    private Float majorPoint;

    private Float attitudePoint;

    private Float softSkillPoint;

    private Float finalPoint;

    public static FinalEvaluationResponse of(FinalEvaluation finalEvaluation) {
        return FinalEvaluationResponse.builder()
                .id(finalEvaluation.getId())
                .startDate(finalEvaluation.getStartDate())
                .endDate(finalEvaluation.getEndDate())
                .duration(finalEvaluation.getDuration())
                .division(finalEvaluation.getDivision())
                .staffCode(finalEvaluation.getStaffCode())
                .comment(finalEvaluation.getComment())
                .allowance(finalEvaluation.getAllowance())
                .majorPoint(finalEvaluation.getFinalPoint())
                .attitudePoint(finalEvaluation.getAttitudePoint())
                .softSkillPoint(finalEvaluation.getSoftSkillPoint())
                .finalPoint(finalEvaluation.getFinalPoint())
                .build();
    }

}
