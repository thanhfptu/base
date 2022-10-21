package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CompanyRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotBlank(message = "Thiếu thông tin Tên công ty!")
    private String name;

    private String address;

    private String website;

    private String description;

    private String imgUrl;

    @NotBlank(message = "Thiếu thông tin Mã số thuế!")
    private String taxCode;

    private Boolean enabled;

    private List<CompanyContactRequest> contacts;

}
