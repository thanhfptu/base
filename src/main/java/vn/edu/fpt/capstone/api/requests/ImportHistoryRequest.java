package vn.edu.fpt.capstone.api.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ImportHistoryRequest {

    @NotNull(message = "Vui lòng chọn Tệp để tải lên!")
    private MultipartFile file;

    @NotNull(message = "Vui lòng chọn loại Tệp!")
    private Integer type;

}
