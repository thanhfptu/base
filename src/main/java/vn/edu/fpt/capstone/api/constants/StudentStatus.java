package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StudentStatus {

    UNKNOWN(-1, "Không xác định"),
    DISABLED(0, "Không hoạt động"),
    ENABLED(1, "Đang hoạt động"),
    NOT_UPDATE_INFO(2, "Chưa cập nhật thông tin");

    private final Integer value;
    private final String name;

    public static StudentStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(StudentStatus.values())
                .filter(status -> value.equals(status.getValue()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<StudentStatus> LIST = List.of(StudentStatus.values());

}
