package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CVStatus {

    UNKNOWN(-1, "Chưa xác định"),
    UNCHECKED(0, "Chưa kiểm tra"),
    FAILED(1, "Chưa đạt"),
    EDITED(2, "Đã sửa"),
    APPROVED(3,"Được chấp nhận");

    private final Integer value;
    private final String name;

    public static CVStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(CVStatus.values())
                .filter(status -> value.equals(status.getValue()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<CVStatus> LIST = List.of(CVStatus.values());

}
