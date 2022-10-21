package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.RegionRequest;
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
@Table(name = Region.TABLE_NAME)
public class Region extends BaseEntity {

    public static final String TABLE_NAME = "regions";

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id")
    private Long parentId;

    public static Region of(RegionRequest request) {
        return Region.builder()
                .name(request.getName())
                .level(request.getLevel())
                .parentId(request.getParentId())
                .build();
    }

    public static Region of(RegionRequest request, Long createdBy) {
        Region build = Region.of(request);
        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }
        return build;
    }
}
