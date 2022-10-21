package vn.edu.fpt.capstone.api.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.converters.CVStatusConverter;

import javax.persistence.*;

@Getter
@Setter

@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = ManageCVDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = ManageCVDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "company_id", type = Long.class),
                        @ColumnResult(name = "company_name", type = String.class),
                        @ColumnResult(name = "job_id", type = Long.class),
                        @ColumnResult(name = "job_title", type = String.class),
                        @ColumnResult(name = "cv_id", type = Long.class),
                        @ColumnResult(name = "url", type = String.class),
                        @ColumnResult(name = "edu_email", type = String.class),
                        @ColumnResult(name = "status", type = Integer.class)
                }))

public class ManageCVDTO {
    public static final String SQL_RESULT_SET_MAPPING = "ManageCVDTO";
    @Id
    private Long id;
    private Long companyId;
    private String companyName;
    private Long jobId;
    private String jobTitle;
    private Long cvId;
    private String cvUrl;
    private String email;
    @Convert(converter = CVStatusConverter.class)
    private CVStatus status;

    public ManageCVDTO(Long id, Long companyId, String companyName, Long jobId, String jobTitle, Long cvId, String cvUrl, String email, Integer status) {
        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.cvId = cvId;
        this.cvUrl = cvUrl;
        this.email = email;
        this.status = CVStatus.of(status);
    }
}
