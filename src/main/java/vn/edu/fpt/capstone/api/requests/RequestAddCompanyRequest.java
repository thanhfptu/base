package vn.edu.fpt.capstone.api.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestAddCompanyRequest {

    private Long id;

    @NotBlank(message = "Thiếu mã số thuế!")
    private String taxCode;

    @NotBlank(message = "Thiếu tên công ty!")
    private String name;

    private MultipartFile offerFile;

    private MultipartFile mouFile;

    private String address;

    private String website;

}
