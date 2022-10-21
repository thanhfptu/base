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
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.MajorResponse;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.MajorRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.major.MajorService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(MajorManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Major", description = "Quản lý Major")
public class MajorManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/majors";

    private final MajorService majorService;

    @GetMapping
    @Operation(summary = "Danh sách Major")
    public ResponseEntity<PageResponse<MajorResponse>> list(@RequestParam Integer currentPage,
                                                            @RequestParam Integer pageSize,
                                                            @RequestParam(required = false) String code,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) Integer level,
                                                            @RequestParam(required = false) Long parentId,
                                                            @RequestParam(required = false) Boolean enabled) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        return AppResponse.success(majorService.list(pageable, code, name, level, parentId, enabled));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Major từ id")
    public ResponseEntity<BaseResponse<MajorResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(majorService.get(id));
    }

    @PostMapping
    @Operation(summary = "Tạo Major")
    public ResponseEntity<BaseResponse<MajorResponse>> create(Authentication authentication, @Valid @RequestBody MajorRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(majorService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật Major")
    public ResponseEntity<BaseResponse<MajorResponse>> update(Authentication authentication, @Valid @RequestBody MajorRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(majorService.save(request, operatorId));
    }
}
