package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.entities.RequestAddCompanyContact;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class RequestAddCompanyContactResponse {
    private Long id;
    private String email;
    private String phone;
    private String name;
    private OJTInfoStatus status;

    public static RequestAddCompanyContactResponse of(RequestAddCompanyContact requestAddCompanyContact){
        return RequestAddCompanyContactResponse.builder()
                .id(requestAddCompanyContact.getId())
                .email(requestAddCompanyContact.getEmail())
                .phone(requestAddCompanyContact.getPhone())
                .name(requestAddCompanyContact.getName())
                .status(requestAddCompanyContact.getStatus())
                .build();
    }
}
