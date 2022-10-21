package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.JobRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Job.TABLE_NAME)
public class Job extends BaseEntity{
    public static final String TABLE_NAME = "jobs";

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "num_of_recruit")
    private Integer numberRecruit;

    @Column(name = "num_of_applied")
    private Integer numberApplied;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "semester_id")
    private Long semesterId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(name = "salary")
    private String salary;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    public static Job of(JobRequest job) {
        Date publishDate = DateUtils.at(DateUtils.toLocalDate(job.getPublishDate()), LocalTime.MIN);
        return Job.builder()
                .title(job.getTitle())
                .benefit(job.getBenefit())
                .description(job.getDescription())
                .requirement(job.getRequirement())
                .numberRecruit(job.getNumberRecruit())
                .numberApplied(0)
                .publishDate(publishDate)
                .expiredDate(job.getExpiredDate())
                .companyId(job.getCompanyId())
                .regionId(job.getRegionId())
                .salary(job.getSalary())
                .imgUrl(job.getImgUrl())
                .isVisible(publishDate.compareTo(DateUtils.now()) <= 0)
                .build();
    }

}
