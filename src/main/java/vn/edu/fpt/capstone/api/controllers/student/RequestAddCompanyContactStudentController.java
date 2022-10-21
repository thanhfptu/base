package vn.edu.fpt.capstone.api.controllers.student;

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
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyContactResponse;
import vn.edu.fpt.capstone.api.services.requestaddcompanycontact.RequestAddCompanyContactService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestAddCompanyContactStudentController.PATH)
@Tag(name = "Student request add company contact", description = "Sinh viên tạo yêu cầu thêm company contact")
public class RequestAddCompanyContactStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/request-add-company-contact";

    public final RequestAddCompanyContactService service;

    @GetMapping
    @Operation
    public ResponseEntity<PageResponse<RequestAddCompanyContactResponse>> list(@RequestParam Integer currentPage,
                                                                               @RequestParam Integer pageSize,
                                                                               @RequestParam(required = false) String email,
                                                                               @RequestParam(required = false) String phone,
                                                                               @RequestParam(required = false) String name,
                                                                               @RequestParam Long createdBy) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<RequestAddCompanyContactResponse> response = service.list(pageable, email, name, phone,createdBy);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyContactResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<RequestAddCompanyContactResponse> response = service.get(id);
        return AppResponse.success(response);
    }

    @PostMapping
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyContactResponse>> create(Authentication authentication,
                                                                                 @Valid @RequestBody RequestAddCompanyContactRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<RequestAddCompanyContactResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }

    @PutMapping
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyContactResponse>> upadate(Authentication authentication,
                                                                                 @Valid @RequestBody RequestAddCompanyContactRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<RequestAddCompanyContactResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }
}
