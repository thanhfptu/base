package vn.edu.fpt.capstone.api.controllers.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.OJTInfoRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.OJTInfoResponse;
import vn.edu.fpt.capstone.api.services.ojtinfo.OJTInfoService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(OJTInfoStudentController.PATH)
@Tag(name = "Student register ojt info", description = "Sinh viên đăng ký thông tin thực tập")
public class OJTInfoStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/ojt-info";

    private final OJTInfoService service;

    @PostMapping
    @Operation
    public ResponseEntity<BaseResponse<OJTInfoResponse>> create(Authentication authentication,
                                                                @Valid @RequestBody OJTInfoRequest request){
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<OJTInfoResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }

    @PutMapping
    @Operation
    public ResponseEntity<BaseResponse<OJTInfoResponse>> update(Authentication authentication,
                                                                @Valid @RequestBody OJTInfoRequest request){
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<OJTInfoResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }
}
