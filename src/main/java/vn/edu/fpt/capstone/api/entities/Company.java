package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.CompanyRequest;
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
@Table(name = Company.TABLE_NAME)
public class Company extends BaseEntity{

    public static final String TABLE_NAME = "companies";

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "website")
    private String website;

    @Column(name = "enabled")
    private Boolean enabled;

    public static Company of(CompanyRequest request) {
        return Company.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .website(request.getWebsite())
                .imgUrl(request.getImgUrl())
                .taxCode(request.getTaxCode())
                .enabled(request.getEnabled())
                .build();
    }

    public static Company of(CompanyRequest request, Long operatorId) {
        Company build = Company.of(request);

        if (Objects.nonNull(operatorId)) {
            build.setCreatedAt(DateUtils.now());
            build.setCreatedBy(operatorId);
        }

        return build;
    }
}
