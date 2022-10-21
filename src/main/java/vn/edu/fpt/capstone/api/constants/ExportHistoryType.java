package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ExportHistoryType {

    UNKNOWN(-1, "Unknown"),
    USER(0, "Tài khoản"),
    CV_SEMESTER(1,"CV theo kỳ học"),
    CV_COMPANY(2, "CV theo công ty");

    private final int value;
    private final String name;

    public static ExportHistoryType of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(ExportHistoryType.values())
                .filter(item -> item.getValue() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<ExportHistoryType> LIST = List.of(ExportHistoryType.values());

}
