package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.converters.CVStatusConverter;
import vn.edu.fpt.capstone.api.requests.UserCVRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = UserCV.TABLE_NAME)
public class UserCV extends BaseEntity {

    public static final String TABLE_NAME = "user_cv";

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url")
    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "status")
    @Convert(converter = CVStatusConverter.class)
    private CVStatus status;

    @Column(name = "enabled")
    private Boolean enabled;

    public static UserCV of(UserCVRequest request) {
        return UserCV.builder()
                .studentId(request.getStudentId())
                .url(request.getUrl())
                .status(CVStatus.of(request.getStatus()))
                .enabled(request.getEnabled())
                .build();
    }

    public static UserCV of(UserCVRequest request, Long createdBy) {
        UserCV build = UserCV.of(request);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(createdBy);
        return build;
    }
}
