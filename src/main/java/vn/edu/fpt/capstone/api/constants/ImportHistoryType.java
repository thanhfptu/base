package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ImportHistoryType {

    UNKNOWN(-1, "Unknown"),
    USER(1, "Tài khoản");

    private final int value;
    private final String name;

    public static ImportHistoryType of(Integer value) {
        if (Objects.isNull(value)) return UNKNOWN;
        return Stream.of(ImportHistoryType.values())
                .filter(item -> item.getValue() == value)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static final List<ImportHistoryType> LIST = List.of(ImportHistoryType.values());

}
