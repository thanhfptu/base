package vn.edu.fpt.capstone.api.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ExportHistoryRequest {

    @NotNull(message = "Vui lòng chọn loại Tệp!")
    private Integer type;

    private Long companyId;

}
