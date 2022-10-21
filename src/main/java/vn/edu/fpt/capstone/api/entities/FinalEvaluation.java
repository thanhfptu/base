package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.FinalEvaluationRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FinalEvaluation.TABLE_NAME)
public class FinalEvaluation extends BaseEntity{
    public static final String TABLE_NAME = "final_evaluation";

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "staff_code")
    private String staffCode;

    @Column(name = "division")
    private String division;

    @Column(name = "comment")
    private String comment;

    @Column(name = "allowance")
    private String allowance;

    @Column(name = "major_point")
    private Float majorPoint;

    @Column(name = "attitude_point")
    private Float attitudePoint;

    @Column(name = "soft_skill_point")
    private Float softSkillPoint;

    @Column(name = "final_point")
    private Float finalPoint;

    public static FinalEvaluation of(FinalEvaluationRequest request) {
        return FinalEvaluation.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .duration(request.getDuration())
                .division(request.getDivision())
                .staffCode(request.getStaffCode())
                .comment(request.getComment())
                .allowance(request.getAllowance())
                .majorPoint(request.getMajorPoint())
                .attitudePoint(request.getAttitudePoint())
                .softSkillPoint(request.getSoftSkillPoint())
                .finalPoint(request.getFinalPoint())
                .build();
    }
}
