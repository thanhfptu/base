package vn.edu.fpt.capstone.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.EvaluationStatus;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Evaluation.TABLE_NAME)
public class Evaluation extends BaseEntity{
    public static final String TABLE_NAME = "evaluation";

    private Long studentId;

    private Long semesterId;

    private Long contactId;

    private Long midtermId;

    private EvaluationStatus midtermStatus;

    private Long finalId;

    private EvaluationStatus finalStatus;

}
