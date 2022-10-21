package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.CampusStatus;
import vn.edu.fpt.capstone.api.converters.CampusStatusConverter;
import vn.edu.fpt.capstone.api.requests.CampusRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Campus.TABLE_NAME)
public class Campus extends BaseEntity {

    public static final String TABLE_NAME = "campuses";

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    @Convert(converter = CampusStatusConverter.class)
    private CampusStatus status;

    public static Campus of(CampusRequest campus) {
        return Campus.builder()
                .name(campus.getName().strip())
                .regionId(campus.getRegionId())
                .status(CampusStatus.of(campus.getStatus()))
                .build();
    }

    public static Campus of(CampusRequest campus, Long createdBy) {
        Campus build = Campus.of(campus);

        if (Objects.nonNull(createdBy)) {
            build.setCreatedBy(createdBy);
            build.setCreatedAt(DateUtils.now());
        }

        return build;
    }

}
