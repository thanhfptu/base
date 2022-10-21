package vn.edu.fpt.capstone.api.controllers.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.userCV.UserCVService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserCVStudentController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User CV Student", description = "Sinh viên quản lý CV bản thân")
public class UserCVStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/cv";

    private final UserCVService userCVService;

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin CV từ id")
    public ResponseEntity<BaseResponse<UserCVResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<UserCVResponse> response = userCVService.get(id);
        return AppResponse.success(response);
    }

    @PostMapping
    @Operation(summary = "Sinh viên upload cv")
    public ResponseEntity<BaseResponse<UserCVResponse>> create(Authentication authentication,
                                                               @Valid @RequestParam("file") MultipartFile file) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        return AppResponse.success(userCVService.upload(file, user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Sinh viên update cv")
    public ResponseEntity<BaseResponse<UserCVResponse>> update(Authentication authentication,
                                                               @Valid @RequestParam("file") MultipartFile file,
                                                               @PathVariable("id") Long id) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        return AppResponse.success(userCVService.update(file, user, id));
    }

    @GetMapping("/student/{id}")
    @Operation(summary = "Lấy danh sách CV từ studentId")
    public ResponseEntity<BaseResponse<List<UserCVResponse>>> getByStudentId(@PathVariable("id") Long id) {
        BaseResponse<List<UserCVResponse>> response = userCVService.getByStudentId(id);
        return AppResponse.success(response);
    }

}
