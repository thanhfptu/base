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
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.RequestAddCompanyRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.requestaddcompany.RequestAddCompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestAddCompanyManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Admin manage request add company", description = "Admin quản lý yêu cầu thêm company")
public class RequestAddCompanyManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/request-add-company";

    public final RequestAddCompanyService service;

    @GetMapping
    @Operation
    public ResponseEntity<PageResponse<RequestAddCompanyResponse>> list(@RequestParam Integer currentPage,
                                                                        @RequestParam Integer pageSize,
                                                                        @RequestParam(required = false) String taxCode,
                                                                        @RequestParam(required = false) String name,
                                                                        @RequestParam(required = false) Long createdBy) {
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

    @PostMapping("/status/{status}")
    @Operation()
    public ResponseEntity<BaseResponse<List<RequestAddCompanyResponse>>> changeStatus(Authentication authentication,
                                                                            @Valid @RequestBody IdsRequest request,
                                                                            @PathVariable("status") Integer status) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(service.changeStatus(request.getIds(), status, operatorId));
    }
}
