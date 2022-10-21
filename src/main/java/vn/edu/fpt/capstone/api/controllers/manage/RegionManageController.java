package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.RegionRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RegionResponse;
import vn.edu.fpt.capstone.api.services.region.RegionService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(RegionManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Region", description = "Quản lý Region")
public class RegionManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/regions";

    private final RegionService regionService;

    @GetMapping
    @Operation(summary = "Danh sách Region")
    public ResponseEntity<PageResponse<RegionResponse>> list(@RequestParam Integer currentPage,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) Integer level,
                                                             @RequestParam(required = false) Long parentId) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        return AppResponse.success(regionService.list(pageable, level, name, parentId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Region từ id")
    public ResponseEntity<BaseResponse<RegionResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(regionService.get(id));
    }

    @PostMapping
    @Operation(summary = "Tạo Region")
    public ResponseEntity<BaseResponse<RegionResponse>> create(Authentication authentication, @Valid @RequestBody RegionRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(regionService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật Region")
    public ResponseEntity<BaseResponse<RegionResponse>> update(Authentication authentication, @Valid @RequestBody RegionRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(regionService.save(request, operatorId));
    }

}
