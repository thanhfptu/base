package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.CVPreview;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CVPreviewResponse {
    private Long id;
    private Long cvId;
    private String imgUrl;

    public static CVPreviewResponse of(CVPreview cvPreview) {
        return CVPreviewResponse.builder()
                .id(cvPreview.getId())
                .cvId(cvPreview.getCvId())
                .imgUrl(cvPreview.getImgUrl())
                .build();
    }
}
