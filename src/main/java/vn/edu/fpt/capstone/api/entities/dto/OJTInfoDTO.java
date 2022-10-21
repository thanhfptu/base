package vn.edu.fpt.capstone.api.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = OJTInfoDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = OJTInfoDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "company_name", type = String.class),
                        @ColumnResult(name = "tax_code", type = String.class),
                        @ColumnResult(name = "add_company_id", type = Long.class),
                        @ColumnResult(name = "request_company_name", type = String.class),
                        @ColumnResult(name = "add_company_status", type = Integer.class),
                        @ColumnResult(name = "request_tax_code", type = String.class),
                        @ColumnResult(name = "company_contact", type = String.class),
                        @ColumnResult(name = "add_company_contact_id", type = Long.class),
                        @ColumnResult(name = "add_company_contact_status", type = Integer.class),
                        @ColumnResult(name = "request_company_contact", type = String.class),
                        @ColumnResult(name = "student_id", type = Long.class),
                        @ColumnResult(name = "student_name", type = String.class),
                        @ColumnResult(name = "position", type = String.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "semester_id", type = Long.class),
                        @ColumnResult(name = "semester_name", type = String.class)
                }))
public class OJTInfoDTO {
    public static final String SQL_RESULT_SET_MAPPING = "OJTInfoDTO";

    @Id
    private Long id;
    private String companyName;
    private String taxCode;
    private Long requestCompanyId;
    private String requestCompanyName;
    private String requestTaxCode;
    private OJTInfoStatus addCompanyStatus;
    private String contact;
    private Long requestCompanyContactId;
    private String requestContact;
    private OJTInfoStatus addCompanyContactStatus;
    private Long studentId;
    private String studentName;
    private String position;
    private OJTInfoStatus status;
    private Long semesterId;
    private String semesterName;

    public OJTInfoDTO(Long id,
                      String companyName,
                      String taxCode,
                      Long requestCompanyId,
                      String requestCompanyName,
                      Integer addCompanyStatus,
                      String requestTaxCode,
                      String contact,
                      Long requestCompanyContactId,
                      Integer addCompanyContactStatus,
                      String requestContact,
                      Long studentId,
                      String studentName,
                      String position,
                      Integer status,
                      Long semesterId,
                      String semesterName) {
        this.id = id;
        this.companyName = companyName;
        this.requestCompanyName = requestCompanyName;
        this.taxCode = taxCode;
        this.requestTaxCode = requestTaxCode;
        this.contact = contact;
        this.requestContact = requestContact;
        this.requestCompanyId = requestCompanyId;
        this.requestCompanyContactId = requestCompanyContactId;
        this.addCompanyContactStatus = Objects.isNull(addCompanyContactStatus)
                ? OJTInfoStatus.APPROVED
                : OJTInfoStatus.of(addCompanyContactStatus);
        this.addCompanyStatus = Objects.isNull(addCompanyStatus)
                ? OJTInfoStatus.APPROVED
                : OJTInfoStatus.of(addCompanyStatus);
        this.studentName = studentName;
        this.studentId = studentId;
        this.position = position;
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.status = OJTInfoStatus.of(status);
    }
}
