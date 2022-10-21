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
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.CampusRequest;
import vn.edu.fpt.capstone.api.responses.CampusResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.campus.CampusService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CampusManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Campus", description = "Quản lý Campus")
public class CampusManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/campuses";

    private final CampusService campusService;

    @GetMapping
    @Operation(summary = "Danh sách Campus")
    public ResponseEntity<PageResponse<CampusResponse>> list(@RequestParam Integer currentPage,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String address,
                                                             @RequestParam(required = false) Long regionId,
                                                             @RequestParam(required = false) String phoneNumber,
                                                             @RequestParam(required = false) String email,
                                                             @RequestParam(required = false) Integer status) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return AppResponse.success(campusService.list(pageable, name, address, regionId, phoneNumber, email, status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Campus từ id")
    public ResponseEntity<BaseResponse<CampusResponse>> get(@PathVariable Long id) {
        return AppResponse.success(campusService.get(id));
    }

    @PostMapping
    @Operation(summary = "Tạo mới Campus")
    public ResponseEntity<BaseResponse<CampusResponse>> create(Authentication authentication,
                                                               @Valid @RequestBody CampusRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(campusService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật thông tin Campus")
    public ResponseEntity<BaseResponse<CampusResponse>> update(Authentication authentication,
                                                               @Valid @RequestBody CampusRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(campusService.save(request, operatorId));
    }

    @PostMapping("/disable")
    @Operation(summary = "Vô hiệu hoá Campus")
    public ResponseEntity<BaseResponse<List<CampusResponse>>> disable(Authentication authentication,
                                                                      @Valid @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(campusService.disable(request, operatorId));
    }

}
