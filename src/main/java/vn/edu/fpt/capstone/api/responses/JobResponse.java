package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Job;
import vn.edu.fpt.capstone.api.entities.Region;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String requirement;
    private String benefit;
    private Integer numberRecruit;
    private Integer numberApplied;
    private CompanyResponse company;
    private SemesterResponse semester;
    private List<MajorResponse> majors;
    private RegionResponse region;
    private Date publishDate;
    private Date expiredDate;
    private String salary;
    private String imgUrl;
    private Boolean isVisible;
    private Boolean isActive;
    private Boolean isFeatured;

    public static JobResponse of(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .benefit(job.getBenefit())
                .company(Objects.isNull(job.getCompanyId()) ? null : CompanyResponse.builder().id(job.getCompanyId()).build())
                .semester(Objects.isNull(job.getSemesterId())? null:SemesterResponse.builder().id(job.getSemesterId()).build())
                .description(job.getDescription())
                .requirement(job.getRequirement())
                .numberRecruit(job.getNumberRecruit())
                .numberApplied(job.getNumberApplied())
                .publishDate(job.getPublishDate())
                .expiredDate(job.getExpiredDate())
                .salary(job.getSalary())
                .imgUrl(job.getImgUrl())
                .isVisible(job.getIsVisible())
                .isActive(job.getIsActive())
                .isFeatured(job.getIsFeatured())
                .build();
    }

    public static JobResponse of(Job job, CompanyResponse company, SemesterResponse semester,List<MajorResponse> majors, RegionResponse region) {
        JobResponse build = JobResponse.of(job);
        build.setCompany(company);
        build.setSemester(semester);
        build.setMajors(majors);
        build.setRegion(region);
        return build;
    }

}
