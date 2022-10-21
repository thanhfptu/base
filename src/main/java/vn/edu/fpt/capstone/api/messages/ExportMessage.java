package vn.edu.fpt.capstone.api.messages;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ExportHistoryType;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.entities.ExportHistory;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportMessage {

    private Long id;
    private String fileName;
    private String sourceURL;
    private ExportHistoryType type;
    private ImportHistoryStatus status;

    public static ExportMessage of(ExportHistory exportHistory) {
        return ExportMessage.builder()
                .id(exportHistory.getId())
                .fileName(exportHistory.getFileName())
                .sourceURL(exportHistory.getSourceURL())
                .type(exportHistory.getType())
                .status(exportHistory.getStatus())
                .build();
    }

}
