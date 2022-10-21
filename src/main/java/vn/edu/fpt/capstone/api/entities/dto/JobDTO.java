package vn.edu.fpt.capstone.api.entities.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = JobDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = JobDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "company_id", type = Long.class),
                        @ColumnResult(name = "company_name", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "requirement", type = String.class),
                        @ColumnResult(name = "benefit", type = String.class),
                        @ColumnResult(name = "num_of_recruit", type = Integer.class),
                        @ColumnResult(name = "num_of_applied", type = Integer.class),
                        @ColumnResult(name = "semester_id", type = Long.class),
                        @ColumnResult(name = "semester_name", type = String.class),
                        @ColumnResult(name = "region_id", type = Long.class),
                        @ColumnResult(name = "region_name", type = String.class),
                        @ColumnResult(name = "publish_date", type = Date.class),
                        @ColumnResult(name = "expired_date", type = Date.class),
                        @ColumnResult(name = "salary", type = String.class),
                        @ColumnResult(name = "img_url", type = String.class),
                        @ColumnResult(name = "is_visible", type = Boolean.class),
                        @ColumnResult(name = "is_active", type = Boolean.class),
                        @ColumnResult(name = "is_featured", type = Boolean.class),
                }))
public class JobDTO {

    public static final String SQL_RESULT_SET_MAPPING = "JobDTO";

    @Id
    private Long id;
    private String title;
    private Long companyId;
    private String companyName;
    private String description;
    private String requirement;
    private String benefit;
    private Integer numberRecruit;
    private Integer numberApplied;
    private Long semesterId;
    private String semesterName;
    private Long regionId;
    private String regionName;
    private Date publishDate;
    private Date expiredDate;
    private String salary;
    private String imgUrl;
    private Boolean isVisible;
    private Boolean isActive;
    private Boolean isFeatured;

}
