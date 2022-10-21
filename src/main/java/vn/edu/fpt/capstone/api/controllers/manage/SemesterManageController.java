package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.SemesterRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.SemesterResponse;
import vn.edu.fpt.capstone.api.services.semester.SemesterService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(SemesterManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Semester", description = "Quản lý Semester")
public class SemesterManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/semesters";

    private final SemesterService semesterService;

    @GetMapping
    @Operation(summary = "Danh sách Semester")
    public ResponseEntity<PageResponse<SemesterResponse>> list(@RequestParam Integer currentPage,
                                                               @RequestParam Integer pageSize,
                                                               @RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String code,
                                                               @RequestParam(required = false) Integer year,
                                                               @RequestParam(required = false) Date startDate,
                                                               @RequestParam(required = false) Date endDate,
                                                               @RequestParam(required = false) Boolean isActive) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return AppResponse.success(semesterService.list(pageable, name, code, year, startDate, endDate, isActive));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Semester từ id")
    public ResponseEntity<BaseResponse<SemesterResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(semesterService.get(id));
    }

    @PostMapping
    @Operation(summary = "Tạo mới Semester")
    public ResponseEntity<BaseResponse<SemesterResponse>> create(Authentication authentication, @Valid @RequestBody SemesterRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(semesterService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật Semester")
    public ResponseEntity<BaseResponse<SemesterResponse>> update(Authentication authentication, @Valid @RequestBody SemesterRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(semesterService.save(request, operatorId));
    }

    @PostMapping("/disable")
    @Operation(summary = "Vô hiệu hoá Semesters")
    public ResponseEntity<BaseResponse<List<SemesterResponse>>> disable(Authentication authentication,
                                                                        @Valid @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(semesterService.disable(request, operatorId));
    }

}
