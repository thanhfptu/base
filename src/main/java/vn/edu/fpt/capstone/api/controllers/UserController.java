package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.requests.UserRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.RoleResponse;
import vn.edu.fpt.capstone.api.responses.UserResponse;
import vn.edu.fpt.capstone.api.services.role.RoleService;
import vn.edu.fpt.capstone.api.services.user.UserService;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.models.AppUserDetails;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.PATH)
@Tag(name = "User", description = "Quản lý User")
public class UserController {

    public static final String PATH = AppConfig.V1_PATH + "/users";

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/current")
    @Operation(summary = "Lấy thông tin User hiện tại")
    public ResponseEntity<BaseResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return AppResponse.error(HttpStatus.FORBIDDEN, "Người dùng không hợp lệ!");
        }
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        Long userId = userDetails.user().getId();
        return AppResponse.success(userService.get(userId));
    }

    @GetMapping("/current/roles")
    @Operation(summary = "Lấy thông tin quyền của User hiện tại")
    public ResponseEntity<BaseResponse<List<RoleResponse>>> getUserRoles(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return AppResponse.error(HttpStatus.FORBIDDEN, "Người dùng không hợp lệ!");
        }
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        Long userId = userDetails.user().getId();
        return AppResponse.success(roleService.getUserRoles(userId));
    }

    @PutMapping("/current")
    @Operation(summary = "Cập nhật thông tin User Profile")
    public ResponseEntity<BaseResponse<UserResponse>> updateProfile(Authentication authentication,
                                                                    @Valid @RequestBody UserRequest userRequest) {
        if (Objects.isNull(authentication)) {
            return AppResponse.error(HttpStatus.FORBIDDEN, "Người dùng không hợp lệ!");
        }
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        Long userId = userDetails.user().getId();
        return AppResponse.success(userService.updateProfile(userRequest, userId));
    }

}
