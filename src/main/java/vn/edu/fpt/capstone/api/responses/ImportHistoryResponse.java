package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.constants.ImportHistoryType;
import vn.edu.fpt.capstone.api.entities.ImportHistory;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportHistoryResponse {

    private Long id;
    private String fileName;
    private String sourceURL;
    private String resultURL;
    private ImportHistoryType type;
    private ImportHistoryStatus status;
    private String message;
    private UserResponse createdBy;
    private Date createdAt;

    public static ImportHistoryResponse of(ImportHistory importHistory) {
        return ImportHistoryResponse.builder()
                .id(importHistory.getId())
                .fileName(importHistory.getFileName())
                .sourceURL(importHistory.getSourceURL())
                .resultURL(importHistory.getResultURL())
                .type(importHistory.getType())
                .status(importHistory.getStatus())
                .message(importHistory.getMessage())
                .createdAt(importHistory.getCreatedAt())
                .build();
    }

    public static ImportHistoryResponse of(ImportHistory importHistory, UserResponse createdBy) {
        ImportHistoryResponse build = of(importHistory);
        build.setCreatedBy(createdBy);
        return build;
    }

}
