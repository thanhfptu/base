package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.CompanyContact;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyContactResponse {

    private Long id;
    private String email;
    private String phone;
    private String name;
    private CompanyResponse company;

    public static CompanyContactResponse of(CompanyContact companyContact) {
        return CompanyContactResponse.builder()
                .id(companyContact.getId())
                .email(companyContact.getEmail())
                .phone(companyContact.getPhone())
                .name(companyContact.getName())
                .build();
    }

    public static CompanyContactResponse of(CompanyContact companyContact, CompanyResponse company) {
        CompanyContactResponse build = CompanyContactResponse.of(companyContact);
        build.setCompany(company);
        return build;
    }
}
