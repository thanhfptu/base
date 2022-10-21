package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.EvaluationComment;
import vn.edu.fpt.capstone.api.converters.EvaluationCommentConverter;
import vn.edu.fpt.capstone.api.requests.MidtermEvaluationRequest;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = MidtermEvaluation.TABLE_NAME)
public class MidtermEvaluation extends BaseEntity{

    public static final String TABLE_NAME = "midterm_evaluation";

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
    @Convert(converter = EvaluationCommentConverter.class)
    private EvaluationComment comment;

    @Column(name = "note")
    private String note;

    public static MidtermEvaluation of(MidtermEvaluationRequest request) {
        return MidtermEvaluation.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .duration(request.getDuration())
                .division(request.getDivision())
                .staffCode(request.getStaffCode())
                .comment(EvaluationComment.of(request.getComment()))
                .note(request.getNote())
                .build();
    }
}
