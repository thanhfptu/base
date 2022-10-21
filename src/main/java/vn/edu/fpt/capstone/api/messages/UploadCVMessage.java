package vn.edu.fpt.capstone.api.messages;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.UserCV;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadCVMessage {
    private Long id;
    private String fileName;

    public static UploadCVMessage of(UserCV cv) {
        return UploadCVMessage.builder()
                .id(cv.getId())
                .fileName(cv.getFileName())
                .build();
    }
}
