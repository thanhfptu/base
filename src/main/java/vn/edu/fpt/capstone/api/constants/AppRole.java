package vn.edu.fpt.capstone.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AppRole {

    ROLE_ADMIN("ROLE_ADMIN", "Administration"),
    ROLE_STAFF("ROLE_STAFF", "Staff"),
    ROLE_STUDENT("ROLE_STUDENT", "Student"),
    ROLE_LINE_MANAGER("LINE_MANAGER", "Line Manager");

    private final String code;
    private final String name;

    public static AppRole of(String code) {
        return Stream.of(AppRole.values())
                .filter(role -> role.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static final String ADMIN = ROLE_ADMIN.getCode();
    public static final String STAFF = ROLE_STAFF.getCode();
    public static final String STUDENT = ROLE_STUDENT.getCode();
    public static final String LINE_MANAGER = ROLE_LINE_MANAGER.getCode();
    public static final List<AppRole> LIST = List.of(AppRole.values());

}
