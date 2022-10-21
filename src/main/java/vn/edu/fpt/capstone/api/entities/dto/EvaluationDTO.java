package vn.edu.fpt.capstone.api.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.EvaluationStatus;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SqlResultSetMapping(name = EvaluationDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = EvaluationDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "student_name", type = String.class),
                        @ColumnResult(name = "roll_number", type = String.class),
                        @ColumnResult(name = "midterm_id", type = Long.class),
                        @ColumnResult(name = "midterm_status", type = Integer.class),
                        @ColumnResult(name = "final_id", type = Long.class),
                        @ColumnResult(name = "final_status", type = Integer.class)
                }))
public class EvaluationDTO {

    public static final String SQL_RESULT_SET_MAPPING = "EvaluationDTO";

    @Id
    private Long id;

    private String studentName;

    private String rollNumber;

    private Long midtermId;

    private EvaluationStatus midtermStatus;

    private Long finalId;

    private EvaluationStatus finalStatus;

    public EvaluationDTO(Long id, String studentName, String rollNumber, Long midtermId, Integer midtermStatus, Long finalId, Integer finalStatus) {
        this.id = id;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.midtermId = midtermId;
        this.midtermStatus = EvaluationStatus.of(midtermStatus);
        this.finalId = finalId;
        this.finalStatus = EvaluationStatus.of(finalStatus);
    }
}
