package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.converters.OJTInfoStatusConverter;
import vn.edu.fpt.capstone.api.requests.OJTInfoRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = OJTInfo.TABLE_NAME)
public class OJTInfo extends BaseEntity{

    public static final String TABLE_NAME = "ojt_information_registration";

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_contact_id")
    private Long companyContactId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "add_company_request_id")
    private Long requestAddCompanyId;

    @Column(name = "add_company_contact_request_id")
    private Long requestAddCompanyContactId;

    @Column(name = "position")
    private String position;

    @Column(name = "status")
    @Convert(converter = OJTInfoStatusConverter.class)
    private OJTInfoStatus status;

    @Column(name = "semester_id")
    private Long semesterId;

    public static OJTInfo of(OJTInfoRequest request) {
        return OJTInfo.builder()
                .companyId(request.getCompanyId())
                .companyContactId(request.getCompanyContactId())
                .position(request.getPosition())
                .build();
    }

    public static OJTInfo of(OJTInfoRequest request, Long operatorId) {
        OJTInfo build = OJTInfo.of(request);
        build.setStudentId(operatorId);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(operatorId);
        return build;
    }
}
