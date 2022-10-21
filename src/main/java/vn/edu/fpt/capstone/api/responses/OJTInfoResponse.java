package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.entities.OJTInfo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class OJTInfoResponse {
    private Long id;

    private CompanyResponse company;

    private SemesterResponse semester;

    private CompanyContactResponse companyContact;

    private RequestAddCompanyResponse requestAddCompany;

    private RequestAddCompanyContactResponse requestAddCompanyContact;

    private UserResponse student;

    private String position;

    private OJTInfoStatus status;

    public static OJTInfoResponse of(OJTInfo ojtInformationRegistration) {
        return OJTInfoResponse.builder()
                .id(ojtInformationRegistration.getId())
                .position(ojtInformationRegistration.getPosition())
                .status(ojtInformationRegistration.getStatus() )
                .build();
    }

    public static OJTInfoResponse of(OJTInfo ojtInformationRegistration,
                                     CompanyResponse companyResponse,
                                     CompanyContactResponse companyContactResponse,
                                     RequestAddCompanyContactResponse requestAddCompanyContactResponse,
                                     RequestAddCompanyResponse requestAddCompanyResponse,
                                     UserResponse student,
                                     SemesterResponse semester){
        OJTInfoResponse build = OJTInfoResponse.of(ojtInformationRegistration);
        build.setCompany(companyResponse);
        build.setCompanyContact(companyContactResponse);
        build.setRequestAddCompany(requestAddCompanyResponse);
        build.setRequestAddCompanyContact(requestAddCompanyContactResponse);
        build.setStudent(student);
        build.setSemester(semester);
        return build;
    }
}
