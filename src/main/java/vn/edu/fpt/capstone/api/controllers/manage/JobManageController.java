package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.dto.JobDTO;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.JobRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.job.JobService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(JobManageController.PATH)
@Tag(name = "Job", description = "Quản lý Job")
public class JobManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/jobs";

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
                                                          @RequestParam(required = false) Long regionId,
                                                          @RequestParam(required = false) String regionName,
                                                          @RequestParam(required = false) Long semesterId,
                                                          @RequestParam(required = false) String semesterName,
                                                          @RequestParam(required = false) Date publishDate,
                                                          @RequestParam(required = false) Date expiredDate,
                                                          @RequestParam(required = false) String salary,
                                                          @RequestParam(required = false) Boolean isVisible,
                                                          @RequestParam(required = false) Boolean isActive,
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
                isVisible,
                isActive,
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

    @PutMapping
    @Operation(summary = "Cập nhật thông tin job")
    public ResponseEntity<BaseResponse<JobResponse>> update(Authentication authentication,
                                                            @Valid @RequestBody JobRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(jobService.save(request, operatorId));
    }

    @PostMapping
    @Operation(summary = "Tạo mới job")
    public ResponseEntity<BaseResponse<JobResponse>> create(Authentication authentication,
                                                            @Valid @RequestBody JobRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(jobService.save(request, operatorId));
    }

    @PostMapping("/disable")
    @Operation(summary = "Vô hiệu hoá Job")
    public ResponseEntity<BaseResponse<List<JobResponse>>> disable(Authentication authentication,
                                                                   @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(jobService.disable(request.getIds(), operatorId));
    }
}
