package vn.edu.fpt.capstone.api.controllers.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.job.JobService;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(JobStudentController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Job", description = "hiển thị Jobs")
public class JobStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/jobs";

    private final JobService jobService;

    @GetMapping
    @Operation(summary = "Danh sách Job")
    public ResponseEntity<PageResponse<JobDTOResponse>> list(@RequestParam Integer currentPage,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String title,
                                                             @RequestParam(required = false) String description,
                                                             @RequestParam(required = false) String requirement,
                                                             @RequestParam(required = false) String benefit,
                                                             @RequestParam(required = false) Integer numberRecruit,
                                                             @RequestParam(required = false) Integer numberApplied,
                                                             @RequestParam(required = false) Long companyId,
                                                             @RequestParam(required = false) String companyName,
                                                             @RequestParam(required = false) Long semesterId,
                                                             @RequestParam(required = false) String semesterName,
                                                             @RequestParam(required = false) Long regionId,
                                                             @RequestParam(required = false) String regionName,
                                                             @RequestParam(required = false) Date publishDate,
                                                             @RequestParam(required = false) Date expiredDate,
                                                             @RequestParam(required = false) String salary,
                                                             @RequestParam(required = false) Boolean isFeatured,
                                                             @RequestParam(required = false) List<Long> majorIds) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<JobDTOResponse> response = jobService.list(
                pageable,
                title,
                description,
                requirement,
                benefit,
                numberRecruit,
                numberApplied,
                companyId,
                companyName,
                semesterId,
                semesterName,
                regionId,
                regionName,
                publishDate,
                expiredDate,
                salary,
                true,
                true,
                isFeatured,
                majorIds);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Job từ id")
    public ResponseEntity<BaseResponse<JobResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<JobResponse> result = jobService.get(id);
        return AppResponse.success(result);
    }
}
