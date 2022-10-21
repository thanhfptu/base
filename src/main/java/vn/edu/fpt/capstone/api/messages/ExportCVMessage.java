package vn.edu.fpt.capstone.api.messages;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.ExportHistory;

@Getter
@Setter

public class ExportCVMessage extends ExportMessage{
    private Long companyId;

    public ExportCVMessage (ExportHistory exportHistory, Long companyId){
        this.setId(exportHistory.getId());
        this.setCompanyId(companyId);
        this.setType(exportHistory.getType());
        this.setFileName(exportHistory.getFileName());
        this.setStatus(exportHistory.getStatus());
        this.setSourceURL(exportHistory.getSourceURL());
    }
}
