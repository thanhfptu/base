package vn.edu.fpt.capstone.api.controllers.manage;

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
import vn.edu.fpt.capstone.api.requests.CompanyContactRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.companyContact.CompanyContactService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyContactManageController.PATH)
@Tag(name = "CompanyContact", description = "Quản lý Company Contact")
public class CompanyContactManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/companyContacts";

    private final CompanyContactService companyContactService;

    @GetMapping
    @Operation(summary = "Danh sách company contact")
    public ResponseEntity<PageResponse<CompanyContactResponse>> list(@RequestParam Integer currentPage,
                                                                     @RequestParam Integer pageSize,
                                                                     @RequestParam(required = false) String email,
                                                                     @RequestParam(required = false) String phone,
                                                                     @RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) Long companyId,
                                                                     @RequestParam(required = false) Boolean enabled) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<CompanyContactResponse> response = companyContactService.list(pageable,
                email,
                phone,
                name,
                companyId,
                enabled);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin company contact từ id")
    public ResponseEntity<BaseResponse<CompanyContactResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<CompanyContactResponse> result = companyContactService.get(id);
        return AppResponse.success(result);
    }

    @PostMapping
    @Operation(summary = "Tạo company contact")
    public ResponseEntity<BaseResponse<CompanyContactResponse>> create(Authentication authentication,
                                                                       @Valid @RequestBody CompanyContactRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(companyContactService.save(request, operator));
    }

    @PutMapping
    @Operation(summary = "Cập nhật company contact")
    public ResponseEntity<BaseResponse<CompanyContactResponse>> update(Authentication authentication,
                                                                       @Valid @RequestBody CompanyContactRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(companyContactService.save(request, operator));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Vô hiệu hóa company contact")
    public ResponseEntity<BaseResponse<CompanyContactResponse>> disable(Authentication authentication,
                                                                        @PathVariable("id") Long id) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(companyContactService.disable(id, operator));
    }
}
