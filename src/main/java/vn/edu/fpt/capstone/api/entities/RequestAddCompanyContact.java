package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.converters.OJTInfoStatusConverter;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyContactRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = RequestAddCompanyContact.TABLE_NAME)
public class RequestAddCompanyContact extends BaseEntity{
    public static final String TABLE_NAME = "add_company_contact_request";

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Convert(converter = OJTInfoStatusConverter.class)
    private OJTInfoStatus status;

    public static RequestAddCompanyContact of(RequestAddCompanyContactRequest request){
        return RequestAddCompanyContact.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .name(request.getName())
                .build();
    }

    public static RequestAddCompanyContact of(RequestAddCompanyContactRequest request, Long operatorId){
        RequestAddCompanyContact build = RequestAddCompanyContact.of(request);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(operatorId);
        return build;
    }
}
