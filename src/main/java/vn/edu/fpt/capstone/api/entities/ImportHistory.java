package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.constants.ImportHistoryType;
import vn.edu.fpt.capstone.api.converters.ImportHistoryStatusConverter;
import vn.edu.fpt.capstone.api.converters.ImportHistoryTypeConverter;
import vn.edu.fpt.capstone.api.requests.ImportHistoryRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ImportHistory.TABLE_NAME)
public class ImportHistory extends BaseEntity {

    public static final String TABLE_NAME = "import_histories";

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "source_url")
    private String sourceURL;

    @Column(name = "result_url")
    private String resultURL;

    @Column(name = "type")
    @Convert(converter = ImportHistoryTypeConverter.class)
    private ImportHistoryType type;

    @Column(name = "status")
    @Convert(converter = ImportHistoryStatusConverter.class)
    private ImportHistoryStatus status;

    @Column(name = "message")
    private String message;

    public static ImportHistory of(ImportHistoryRequest request) {
        return ImportHistory.builder()
                .fileName(request.getFile().getOriginalFilename())
                .type(ImportHistoryType.of(request.getType()))
                .status(ImportHistoryStatus.NEW)
                .build();
    }

    public static ImportHistory of(ImportHistoryRequest request, Long createdBy) {
        ImportHistory build = of(request);
        if (Objects.isNull(createdBy)) return build;
        build.setCreatedBy(createdBy);
        build.setCreatedAt(DateUtils.now());
        return build;
    }

}
