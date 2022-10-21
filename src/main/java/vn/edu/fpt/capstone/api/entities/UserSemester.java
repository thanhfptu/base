package vn.edu.fpt.capstone.api.entities;

import lombok.*;
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
@Table(name = UserSemester.TABLE_NAME)
public class UserSemester extends BaseEntity {

    public static final String TABLE_NAME = "user_semesters";

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "semester_id")
    private Long semesterId;

    public static UserSemester of(Long userId, Long semesterId) {
        return UserSemester.builder()
                .userId(userId)
                .semesterId(semesterId)
                .build();
    }

    public static UserSemester of(Long userId, Long semesterId, User createdBy) {
        UserSemester build = UserSemester.of(userId, semesterId);
        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy.getId());
            build.setCreatedAt(DateUtils.now());
        }
        return build;
    }

}
