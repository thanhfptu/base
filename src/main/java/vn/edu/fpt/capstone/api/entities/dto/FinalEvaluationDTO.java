package vn.edu.fpt.capstone.api.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.EvaluationStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SqlResultSetMapping(name = FinalEvaluationDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = FinalEvaluationDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "semester", type = String.class),
                        @ColumnResult(name = "company_name", type = String.class),
                        @ColumnResult(name = "start_date", type = Date.class),
                        @ColumnResult(name = "end_date", type = Date.class),
                        @ColumnResult(name = "duration", type = Integer.class),
                        @ColumnResult(name = "roll_number", type = String.class),
                        @ColumnResult(name = "staff_code", type = String.class),
                        @ColumnResult(name = "full_name", type = String.class),
                        @ColumnResult(name = "division", type = String.class),
                        @ColumnResult(name = "contact_name", type = String.class),
                        @ColumnResult(name = "comment", type = String.class),
                        @ColumnResult(name = "allowance", type = String.class),
                        @ColumnResult(name = "major_point", type = Float.class),
                        @ColumnResult(name = "attitude_point", type = Float.class),
                        @ColumnResult(name = "soft_skill_point", type = Float.class),
                        @ColumnResult(name = "final_point", type = Float.class),
                        @ColumnResult(name = "status", type = Integer.class)
                }))
public class FinalEvaluationDTO {
    public static final String SQL_RESULT_SET_MAPPING = "FinalEvaluationDTO";
    @Id
    private Long id;
    private String semester;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private Integer duration;
    private String rollNumber;
    private String staffCode;
    private String studentName;
    private String division;
    private String contactName;
    private String comment;
    private String allowance;
    private Float majorPoint;
    private Float attitudePoint;
    private Float softSkillPoint;
    private Float finalPoint;
    private EvaluationStatus status;

    public FinalEvaluationDTO(Long id, String semester, String companyName, Date startDate, Date endDate, Integer duration, String rollNumber, String staffCode, String studentName, String division, String contactName, String comment, String allowance, Float majorPoint, Float attitudePoint, Float softSkillPoint, Float finalPoint, Integer status) {
        this.id = id;
        this.semester = semester;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.rollNumber = rollNumber;
        this.staffCode = staffCode;
        this.studentName = studentName;
        this.division = division;
        this.contactName = contactName;
        this.comment = comment;
        this.allowance = allowance;
        this.majorPoint = majorPoint;
        this.attitudePoint = attitudePoint;
        this.softSkillPoint = softSkillPoint;
        this.finalPoint = finalPoint;
        this.status = EvaluationStatus.of(status);
    }
}
