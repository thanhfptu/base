package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.RoleRequest;
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
@Table(name = Role.TABLE_NAME)
public class Role extends BaseEntity {

    public static final String TABLE_NAME = "roles";

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public static Role of(RoleRequest request) {
        return Role.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();
    }

    public static Role of(RoleRequest role, Long createdBy) {
        Role build = Role.of(role);

        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }

        return build;
    }

}
