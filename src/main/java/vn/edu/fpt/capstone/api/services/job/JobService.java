package vn.edu.fpt.capstone.api.services.job;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.entities.dto.JobDTO;
import vn.edu.fpt.capstone.api.requests.JobRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.JobDTOResponse;
import vn.edu.fpt.capstone.api.responses.JobResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;
import java.util.List;

public interface JobService {

    String SERVICE_NAME = "JobService";

    PageResponse<JobDTOResponse> list(Pageable pageable,
                                      String title,
                                      String description,
                                      String requirement,
                                      String benefit,
                                      Integer numberRecruit,
                                      Integer numberApplied,
                                      Long companyId,
                                      String companyName,
                                      Long semesterId,
                                      String semesterName,
                                      Long regionId,
                                      String regionName,
                                      Date publishDate,
                                      Date expiredDate,
                                      String salary,
                                      Boolean isVisible,
                                      Boolean isActive,
                                      Boolean isFeatured,
                                      List<Long> majorIds);

    BaseResponse<JobResponse> get(Long id);

    BaseResponse<JobResponse> create(JobRequest request,
                                     Company company,
                                     List<Major> majors,
                                     Region region,
                                     Long operatorId);

    BaseResponse<JobResponse> update(JobRequest request,
                                     Company company,
                                     List<Major> majors,
                                     Region region,
                                     Long operatorId);

    BaseResponse<JobResponse> save(JobRequest request, Long operatorId);

    BaseResponse<JobResponse> response(Job job,
                                       Company company,
                                       Semester semester,
                                       Region region,
                                       List<Major> majors);

    BaseResponse<List<JobResponse>> disable(List<Long> jobId,
                                            Long operatorId);
}
