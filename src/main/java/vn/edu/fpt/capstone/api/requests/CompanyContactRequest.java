package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CompanyContactRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotBlank(message = "Thiếu thông tin email!")
    private String email;

    @NotBlank(message = "Thiếu thông tin phone!")
    private String phone;

    @NotBlank(message = "Thiếu thông tin name!")
    private String name;

    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;

}
