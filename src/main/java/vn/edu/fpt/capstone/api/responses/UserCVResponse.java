package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.entities.CVPreview;
import vn.edu.fpt.capstone.api.entities.ImportHistory;
import vn.edu.fpt.capstone.api.entities.UserCV;
import vn.edu.fpt.capstone.api.entities.dto.AppliedJobDTO;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCVResponse {
    private Long id;
    private String url;
    private String thumbnailUrl;
    private CVStatus status;
    private Boolean enabled;
    private List<CVPreviewResponse> cvPreview;
    private List<AppliedJobDTO> appliedJobs;
    public static UserCVResponse of(UserCV userCV) {
        return UserCVResponse.builder()
                .id(userCV.getId())
                .url(userCV.getUrl())
                .thumbnailUrl(userCV.getThumbnailUrl())
                .status(userCV.getStatus())
                .enabled(userCV.getEnabled())
                .build();
    }

    public static UserCVResponse of(UserCV userCV, List<CVPreviewResponse> cvPreview, List<AppliedJobDTO> appliedJobs) {
        UserCVResponse build = UserCVResponse.of(userCV);
        build.setAppliedJobs(appliedJobs);
        build.setCvPreview(cvPreview);
        return build;
    }
}
