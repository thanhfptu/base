package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.converters.OJTInfoStatusConverter;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RequestAddCompany.TABLE_NAME)
public class RequestAddCompany extends BaseEntity{
    public static final String TABLE_NAME = "add_company_request";

    @Column(name = "offer_url")
    private String offerUrl;

    @Column(name = "mou_url")
    private String mouUrl;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "website")
    private String website;

    @Column(name = "status")
    @Convert(converter = OJTInfoStatusConverter.class)
    private OJTInfoStatus status;

    public static RequestAddCompany of(RequestAddCompanyRequest request){
        return  RequestAddCompany.builder()
                .taxCode(request.getTaxCode())
                .name(request.getName())
                .address(request.getAddress())
                .website(request.getWebsite())
                .build();
    }

    public static RequestAddCompany of(RequestAddCompanyRequest request, Long operatorId){
        RequestAddCompany build = RequestAddCompany.of(request);
        build.setCreatedBy(operatorId);
        build.setCreatedAt(DateUtils.now());
        return build;
    }
}
