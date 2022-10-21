package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CampusStatus {

    UNKNOWN(-1, "Không xác định"),
    INACTIVE(0, "Không hoạt động"),
    ACTIVE(1, "Đang hoạt động");

    private final int value;
    private final String name;

    public static CampusStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(CampusStatus.values())
                .filter(item -> item.value == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<CampusStatus> LIST = List.of(CampusStatus.values());

}
