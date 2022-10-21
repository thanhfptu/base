package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Major;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorResponse {

    private Long id;
    private String code;
    private String name;
    private Integer level;
    private MajorResponse parent;
    private Boolean enabled;

    public static MajorResponse of(Major major) {
        return MajorResponse.builder()
                .id(major.getId())
                .code(major.getCode())
                .name(major.getName())
                .level(major.getLevel())
                .enabled(major.getEnabled())
                .build();
    }

    public static MajorResponse of(Major major, MajorResponse parentMajor) {
        MajorResponse build = MajorResponse.of(major);
        build.setParent(parentMajor);
        return build;
    }

}
