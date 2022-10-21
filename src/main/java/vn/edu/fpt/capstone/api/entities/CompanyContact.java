package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.CompanyContactRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CompanyContact.TABLE_NAME)
public class CompanyContact extends BaseEntity {

    public static final String TABLE_NAME = "company_contacts";

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "company_id")
    private Long companyId;

    public static CompanyContact of(CompanyContactRequest request) {
        return CompanyContact.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .name(request.getName())
                .companyId(request.getCompanyId())
                .build();
    }

    public static CompanyContact of(CompanyContactRequest request, Long operatorId) {
        CompanyContact build = CompanyContact.of(request);
        if (Objects.nonNull(operatorId)) {
            build.setCreatedAt(DateUtils.now());
            build.setCreatedBy(operatorId);
        }
        return build;
    }
}
