package vn.edu.fpt.capstone.api.entities.dto;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.EvaluationComment;
import vn.edu.fpt.capstone.api.constants.EvaluationStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SqlResultSetMapping(name = MidtermEvaluationDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = MidtermEvaluationDTO.class,
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
                        @ColumnResult(name = "comment", type = Integer.class),
                        @ColumnResult(name = "note", type = String.class),
                        @ColumnResult(name = "status", type = Integer.class)
                }))
public class MidtermEvaluationDTO {
    public static final String SQL_RESULT_SET_MAPPING = "MidtermEvaluationDTO";
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
    private EvaluationComment comment;
    private String note;

    private EvaluationStatus status;

    public MidtermEvaluationDTO(Long id,
                                String semester,
                                String companyName,
                                Date startDate,
                                Date endDate,
                                Integer duration,
                                String rollNumber,
                                String staffCode,
                                String studentName,
                                String division,
                                String contactName,
                                Integer comment,
                                String note,
                                Integer status) {
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
        this.comment = EvaluationComment.of(comment);
        this.note = note;
        this.status = EvaluationStatus.of(status);
    }
}
