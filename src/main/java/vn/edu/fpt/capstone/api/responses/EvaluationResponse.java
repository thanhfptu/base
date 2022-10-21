package vn.edu.fpt.capstone.api.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.EvaluationStatus;
import vn.edu.fpt.capstone.api.entities.Evaluation;

@Getter
@Setter
@Builder
public class EvaluationResponse {
    private UserResponse student;

    private SemesterResponse semester;

    private CompanyContactResponse contact;

    private MidtermEvaluationResponse midtermEvaluation;

    private EvaluationStatus midtermStatus;

    private FinalEvaluationResponse finalEvaluation;

    private EvaluationStatus finalStatus;

    public static EvaluationResponse of(Evaluation evaluation){
        return EvaluationResponse.builder()
                .midtermStatus(evaluation.getMidtermStatus())
                .finalStatus(evaluation.getFinalStatus())
                .build();
    }

    public static EvaluationResponse of(Evaluation evaluation,
                                        UserResponse student,
                                        SemesterResponse semester,
                                        CompanyContactResponse contact,
                                        MidtermEvaluationResponse midtermEvaluation,
                                        FinalEvaluationResponse finalEvaluation) {
        EvaluationResponse build = EvaluationResponse.of(evaluation);
        build.setStudent(student);
        build.setContact(contact);
        build.setSemester(semester);
        build.setMidtermEvaluation(midtermEvaluation);
        build.setFinalEvaluation(finalEvaluation);
        return build;
    }
}
