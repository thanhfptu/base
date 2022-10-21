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
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.dto.ManageCVDTO;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.UserCVRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.UserCVResponse;
import vn.edu.fpt.capstone.api.services.userCV.UserCVService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserCVManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User CV", description = "Quản lý User CV")
public class UserCVManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/cv";

    private final UserCVService userCVService;

    @GetMapping
    @Operation(summary = "Danh sách CV")
    public ResponseEntity<PageResponse<ManageCVDTO>> list(@RequestParam Integer currentPage,
                                                          @RequestParam Integer pageSize,
                                                          @RequestParam(required = false) Long companyId,
                                                          @RequestParam(required = false) Long jobId,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) Integer status) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<ManageCVDTO> response = userCVService.list(pageable,
                companyId,
                jobId,
                email,
                status);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin CV từ id")
    public ResponseEntity<BaseResponse<UserCVResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<UserCVResponse> response = userCVService.get(id);
        return AppResponse.success(response);
    }

    @GetMapping("/student/{id}")
    @Operation(summary = "Lấy danh sách CV từ studentId")
    public ResponseEntity<BaseResponse<List<UserCVResponse>>> getByStudentId(@PathVariable("id") Long id) {
        BaseResponse<List<UserCVResponse>> response = userCVService.getByStudentId(id);
        return AppResponse.success(response);
    }

    @PostMapping("/change-status/{status}")
    @Operation(summary = "change status CV")
    public ResponseEntity<BaseResponse<List<UserCVResponse>>> changeStatus(Authentication authentication,
                                                                      @Valid @RequestBody IdsRequest request,
                                                                      @PathVariable("status") Integer status) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(userCVService.changeStatus(request.getIds(), status, operatorId));
    }

    @PutMapping("")
    @Operation(summary = "update CV")
    public ResponseEntity<BaseResponse<UserCVResponse>> update(Authentication authentication, @Valid @RequestBody UserCVRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(userCVService.updateApprove(request, operatorId));
    }
}
