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
import vn.edu.fpt.capstone.api.entities.dto.OJTInfoDTO;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.ojtinfo.OJTInfoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(OJTInfoMangeController.PATH)
@Tag(name = "Admin review ojt info", description = "admin quản lý thông tin đang ký thực tập")
public class OJTInfoMangeController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/ojt-info";

    private final OJTInfoService service;

    @GetMapping
    @Operation
    public ResponseEntity<PageResponse<OJTInfoDTO>> list(@RequestParam Integer currentPage,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam(required = false) String taxCode,
                                                         @RequestParam(required = false) Long companyId,
                                                         @RequestParam(required = false) Long contactId,
                                                         @RequestParam(required = false) String requestTaxCode,
                                                         @RequestParam(required = false) String companyName,
                                                         @RequestParam(required = false) String contactName,
                                                         @RequestParam(required = false) String requestCompanyName,
                                                         @RequestParam(required = false) String requestContactName,
                                                         @RequestParam(required = false) String studentName,
                                                         @RequestParam(required = false) Integer status,
                                                         @RequestParam(required = false) String position,
                                                         @RequestParam(required = false) Long semesterId) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<OJTInfoDTO> response = service.list(pageable,
                taxCode,
                companyId,
                contactId,
                studentName,
                requestTaxCode,
                companyName,
                contactName,
                requestCompanyName,
                requestContactName,
                status,
                position,
                semesterId);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<BaseResponse<OJTInfoResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<OJTInfoResponse> response = service.get(id);
        return AppResponse.success(response);
    }

    @PostMapping("/status/{status}")
    @Operation()
    public ResponseEntity<BaseResponse<List<OJTInfoResponse>>> changeStatus(Authentication authentication,
                                                                           @Valid @RequestBody IdsRequest request,
                                                                           @PathVariable("status") Integer status) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(service.changeStatus(request.getIds(), status, operatorId));
    }
}
