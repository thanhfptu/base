package vn.edu.fpt.capstone.api.controllers.student;


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
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RequestAddCompanyResponse;
import vn.edu.fpt.capstone.api.services.requestaddcompany.RequestAddCompanyService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestAddCompanyStudentController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Student request add company", description = "Sinh viên tạo yêu cầu thêm company")
public class RequestAddCompanyStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/request-add-company";

    public final RequestAddCompanyService service;

    @GetMapping
    @Operation
    public ResponseEntity<PageResponse<RequestAddCompanyResponse>> list(@RequestParam Integer currentPage,
                                                                        @RequestParam Integer pageSize,
                                                                        @RequestParam(required = false) String taxCode,
                                                                        @RequestParam(required = false) String name,
                                                                        @RequestParam Long createdBy) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<RequestAddCompanyResponse> response = service.list(pageable, taxCode, name, createdBy);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<RequestAddCompanyResponse> response = service.get(id);
        return AppResponse.success(response);
    }

    @PostMapping
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyResponse>> create(Authentication authentication,
                                                                          @Valid @ModelAttribute RequestAddCompanyRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<RequestAddCompanyResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }

    @PutMapping
    @Operation
    public ResponseEntity<BaseResponse<RequestAddCompanyResponse>> upadate(Authentication authentication,
                                                                                  @Valid @ModelAttribute RequestAddCompanyRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        BaseResponse<RequestAddCompanyResponse> response = service.save(request, operator.getId());
        return AppResponse.success(response);
    }
}
