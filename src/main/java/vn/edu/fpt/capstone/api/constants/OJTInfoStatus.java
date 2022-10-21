package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OJTInfoStatus {
    UNKNOWN(-1, "Không xác định"),
    UNCHECKED(0, "Chưa kiểm tra"),
    APPROVED(1, "Chấp nhận"),
    REFUSED(2,"Từ chối"),
    NEED_VALIDATION(3,"Cần xác minh công ty");

    private final int value;
    private final String name;

    public static OJTInfoStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(OJTInfoStatus.values())
                .filter(item -> item.getValue() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<OJTInfoStatus> LIST = List.of(OJTInfoStatus.values());
}
