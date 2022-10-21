package vn.edu.fpt.capstone.api.messages;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.constants.ImportHistoryType;
import vn.edu.fpt.capstone.api.entities.ImportHistory;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportMessage {

    private Long id;
    private String fileName;
    private String sourceURL;
    private String resultURL;
    private ImportHistoryType type;
    private ImportHistoryStatus status;
    private String message;

    public static ImportMessage of(ImportHistory importHistory) {
        return ImportMessage.builder()
                .id(importHistory.getId())
                .fileName(importHistory.getFileName())
                .sourceURL(importHistory.getSourceURL())
                .resultURL(importHistory.getResultURL())
                .type(importHistory.getType())
                .status(importHistory.getStatus())
                .build();
    }

}
