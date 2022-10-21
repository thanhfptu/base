package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Major;
import vn.edu.fpt.capstone.api.entities.dto.JobDTO;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTOResponse {
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
    private List<MajorResponse> majors;

    public static JobDTOResponse of(JobDTO jobDTO) {
        return JobDTOResponse.builder()
                .id(jobDTO.getId())
                .title(jobDTO.getTitle())
                .companyId(jobDTO.getCompanyId())
                .companyName(jobDTO.getCompanyName())
                .description(jobDTO.getDescription())
                .requirement(jobDTO.getRequirement())
                .benefit(jobDTO.getBenefit())
                .numberRecruit(jobDTO.getNumberRecruit())
                .numberApplied(jobDTO.getNumberApplied())
                .semesterId(jobDTO.getSemesterId())
                .semesterName(jobDTO.getSemesterName())
                .regionId(jobDTO.getRegionId())
                .regionName(jobDTO.getRegionName())
                .publishDate(jobDTO.getPublishDate())
                .expiredDate(jobDTO.getExpiredDate())
                .salary(jobDTO.getSalary())
                .imgUrl(jobDTO.getImgUrl())
                .isVisible(jobDTO.getIsVisible())
                .isActive(jobDTO.getIsActive())
                .isFeatured(jobDTO.getIsFeatured())
                .build();
    }

    public static JobDTOResponse of(JobDTO jobDTO, List<MajorResponse> majors) {
        JobDTOResponse build = JobDTOResponse.of(jobDTO);
        build.setMajors(majors);
        return build;
    }
}
