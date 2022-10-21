package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ExportHistoryType;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.entities.ExportHistory;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportHistoryResponse {

    private Long id;
    private String fileName;
    private String sourceURL;
    private ExportHistoryType type;
    private ImportHistoryStatus status;
    private UserResponse createdBy;
    private Date createdAt;

    public static ExportHistoryResponse of(ExportHistory exportHistory) {
        return ExportHistoryResponse.builder()
                .id(exportHistory.getId())
                .fileName(exportHistory.getFileName())
                .sourceURL(exportHistory.getSourceURL())
                .type(exportHistory.getType())
                .status(exportHistory.getStatus())
                .createdAt(exportHistory.getCreatedAt())
                .build();
    }

    public static ExportHistoryResponse of(ExportHistory exportHistory, UserResponse createdBy) {
        ExportHistoryResponse build = of(exportHistory);
        build.setCreatedBy(createdBy);
        return build;
    }

}
