package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StudentOJTStatus {

    UNKNOWN(0, "Không xác định"),
    NOT_YET(1, "Chưa thực tập"),
    DOING(2, "Đang thực tập"),
    FINISHED(3, "Đã thực tập");

    private final Integer value;
    private final String name;

    public static StudentOJTStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(StudentOJTStatus.values())
                .filter(status -> value.equals(status.getValue()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<StudentOJTStatus> LIST = List.of(StudentOJTStatus.values());

}
