package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Company;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private String address;
    private String description;
    private String website;
    private String imgUrl;
    private String taxCode;
    private List<CompanyContactResponse> contacts;
    private Boolean enabled;

    public static CompanyResponse of(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .description(company.getDescription())
                .imgUrl(company.getImgUrl())
                .website(company.getWebsite())
                .taxCode(company.getTaxCode())
                .enabled(company.getEnabled())
                .build();
    }

    public static CompanyResponse of(Company company, List<CompanyContactResponse> contacts) {
        CompanyResponse build = CompanyResponse.of(company);
        build.setContacts(contacts);
        return build;
    }
}
