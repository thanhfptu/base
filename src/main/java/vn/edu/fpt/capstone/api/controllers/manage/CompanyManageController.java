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
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.CompanyRequest;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CompanyResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.company.CompanyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Company", description = "Quản lý Company")
public class CompanyManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/companies";

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "Danh sách company")
    public ResponseEntity<PageResponse<CompanyResponse>> list(@RequestParam Integer currentPage,
                                                              @RequestParam Integer pageSize,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String address,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam(required = false) String website,
                                                              @RequestParam(required = false) String imgUrl,
                                                              @RequestParam(required = false) String taxCode,
                                                              @RequestParam(required = false) Boolean enabled) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        PageResponse<CompanyResponse> response = companyService.list(pageable,
                name,
                address,
                description,
                website,
                imgUrl,
                taxCode,
                enabled);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin company từ id")
    public ResponseEntity<BaseResponse<CompanyResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<CompanyResponse> result = companyService.get(id);
        return AppResponse.success(result);
    }

    @PostMapping
    @Operation(summary = "Tạo company")
    public ResponseEntity<BaseResponse<CompanyResponse>> create(Authentication authentication,
                                                                @Valid @RequestBody CompanyRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(companyService.save(request, operatorId));
    }

    @PutMapping
    @Operation(summary = "Cập nhật company")
    public ResponseEntity<BaseResponse<CompanyResponse>> update(Authentication authentication,
                                                                @Valid @RequestBody CompanyRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(companyService.save(request, operatorId));
    }

    @PostMapping("/disable")
    @Operation(summary = "Vô hiệu hóa company")
    public ResponseEntity<BaseResponse<List<CompanyResponse>>> disable(Authentication authentication,
                                                                       @Valid @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(companyService.disable(request, operatorId));
    }
}
