package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.ExportHistoryType;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.constants.ImportHistoryType;
import vn.edu.fpt.capstone.api.converters.ExportHistoryTypeConverter;
import vn.edu.fpt.capstone.api.converters.ImportHistoryStatusConverter;
import vn.edu.fpt.capstone.api.requests.ExportHistoryRequest;
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
@Table(name = ExportHistory.TABLE_NAME)
public class ExportHistory extends BaseEntity{

    public static final String TABLE_NAME = "export_histories";

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "source_url")
    private String sourceURL;

    @Column(name = "type")
    @Convert(converter = ExportHistoryTypeConverter.class)
    private ExportHistoryType type;

    @Column(name = "status")
    @Convert(converter = ImportHistoryStatusConverter.class)
    private ImportHistoryStatus status;

    public static ExportHistory of(ExportHistoryRequest request) {
        return ExportHistory.builder()
                .type(ExportHistoryType.of(request.getType()))
                .status(ImportHistoryStatus.PENDING)
                .build();
    }

    public static ExportHistory of(ExportHistoryRequest request, Long createdBy) {
        ExportHistory build = of(request);
        if (Objects.isNull(createdBy)) return build;
        build.setCreatedBy(createdBy);
        build.setCreatedAt(DateUtils.now());
        return build;
    }
}
