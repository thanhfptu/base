package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OJTInfoRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;

    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyContactId;

    private RequestAddCompanyRequest requestAddCompany;

    private RequestAddCompanyContactRequest requestAddCompanyContact;

    @NotBlank(message = "Thiếu vị trí thực tập!")
    private String position;

}
