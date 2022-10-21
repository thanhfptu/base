package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = UserRole.TABLE_NAME)
public class UserRole extends BaseEntity {

    public static final String TABLE_NAME = "user_roles";

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    public static UserRole of(Long userId, Long roleId) {
        return UserRole.builder()
                .userId(userId)
                .roleId(roleId)
                .build();
    }

    public static UserRole of(Long userId, Long roleId, Long createdBy) {
        UserRole build = UserRole.of(userId, roleId);

        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }

        return build;
    }

}
