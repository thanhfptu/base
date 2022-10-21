package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.UserRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.UserResponse;
import vn.edu.fpt.capstone.api.services.user.UserService;
import vn.edu.fpt.capstone.api.configs.AppConfig;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User", description = "Quản lý User")
public class UserManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/users";

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Danh sách User")
    public ResponseEntity<PageResponse<UserResponse>> list(@RequestParam Integer currentPage,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) String fullName,
                                                           @RequestParam(required = false) String rollNumber,
                                                           @RequestParam(required = false) Integer gender,
                                                           @RequestParam(required = false) Integer status,
                                                           @RequestParam(required = false) List<Long> majorIds,
                                                           @RequestParam(required = false) Long campusId,
                                                           @RequestParam(required = false) String eduEmail,
                                                           @RequestParam(required = false) String personalEmail,
                                                           @RequestParam(required = false) String phoneNumber,
                                                           @RequestParam(required = false) String address,
                                                           @RequestParam(required = false) Integer ojtStatus) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        PageResponse<UserResponse> response = userService.list(
                pageable,
                fullName,
                rollNumber,
                gender,
                status,
                majorIds,
                campusId,
                eduEmail,
                personalEmail,
                phoneNumber,
                address,
                ojtStatus
        );
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin User từ id")
    public ResponseEntity<BaseResponse<UserResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<UserResponse> result = userService.get(id);
        return AppResponse.success(result);
    }

    @GetMapping("/rollNumber/{rollNumber}")
    @Operation(summary = "Lấy thông tin User từ rollNumber")
    public ResponseEntity<BaseResponse<UserResponse>> get(@PathVariable("rollNumber") String rollNumber) {
        BaseResponse<UserResponse> result = userService.get(rollNumber);
        return AppResponse.success(result);
    }

    @PostMapping
    @Operation(summary = "Tạo mới User")
    public ResponseEntity<BaseResponse<UserResponse>> create(Authentication authentication,
                                                             @Valid @RequestBody UserRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(userService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật thông tin User")
    public ResponseEntity<BaseResponse<UserResponse>> update(Authentication authentication,
                                                             @Valid @RequestBody UserRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(userService.save(request, operatorId));
    }

    @PostMapping("/disable")
    @Operation(summary = "Vô hiệu hoá User")
    public ResponseEntity<BaseResponse<List<UserResponse>>> disable(Authentication authentication,
                                                                    @Valid @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(userService.disable(request.getIds(), operatorId));
    }

//    @GetMapping("/export")
//    @Operation(summary = "Export users ra file excel")
//    public ResponseEntity<Resource> export() throws IOException {
//
//        InputStreamResource file = new InputStreamResource(userService.export());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx")
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(file);
//    }

}
