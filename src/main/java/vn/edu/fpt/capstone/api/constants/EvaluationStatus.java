package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum EvaluationStatus {
    UNKNOWN(-1, "Không xác định"),
    LOCKED(0, "Chưa mở đánh giá"),
    UNLOCKED(1, "Đã mở đánh giá"),
    UNGRADE(2,"Chưa đánh giá");

    final Integer value;
    final String name;

    public static EvaluationStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(EvaluationStatus.values())
                .filter(status -> value.equals(status.getValue()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<EvaluationStatus> LIST = List.of(EvaluationStatus.values());
}
