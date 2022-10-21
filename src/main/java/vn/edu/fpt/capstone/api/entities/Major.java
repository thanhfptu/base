package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.MajorRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Major.TABLE_NAME)
public class Major extends BaseEntity {

    public static final String TABLE_NAME = "majors";

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "enabled")
    private Boolean enabled;

    public static Major of(String code, String name, Integer level, Long parentId) {
        return Major.builder()
                .code(code)
                .name(name)
                .level(level)
                .parentId(parentId)
                .build();
    }

    public static Major of(String code, String name, Integer level, Long parentId, User createdBy) {
        Major build = Major.of(code, name, level, parentId);
        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy.getId());
            build.setCreatedAt(DateUtils.now());
        }
        return build;
    }

    public static Major of(MajorRequest major) {
        return Major.builder()
                .code(major.getCode())
                .name(major.getName())
                .level(major.getLevel())
                .parentId(major.getParentId())
                .build();
    }

    public static Major of(MajorRequest major, Long createdBy) {
        Major build = Major.of(major);
        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }
        return build;
    }

}
