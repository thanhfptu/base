package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ImportHistoryStatus {

    UNKNOWN(-1, "Không xác định"),
    NEW(0, "Mới"),
    PENDING(1, "Chờ xử lý"),
    PROCESSING(2, "Đang xử lý"),
    FAILED(3, "Thất bại"),
    SUCCEEDED(4, "Thành công");

    private final int value;
    private final String name;

    public static ImportHistoryStatus of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(ImportHistoryStatus.values())
                .filter(item -> item.getValue() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<ImportHistoryStatus> LIST = List.of(ImportHistoryStatus.values());

}
