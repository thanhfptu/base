package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum EvaluationComment {

    UNKNOWN(-1, "Không xác định"),
    POOR(0, "Kém"),
    AVERAGE(1, "Trung bình"),
    GOOD(2, "Trung bình khá"),
    VERY_GOOD(3, "Khá"),
    EXCELLENT(4, "Tốt");

    final Integer value;
    final String name;

    public static EvaluationComment of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(EvaluationComment.values())
                .filter(status -> value.equals(status.getValue()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<EvaluationComment> LIST = List.of(EvaluationComment.values());
}
