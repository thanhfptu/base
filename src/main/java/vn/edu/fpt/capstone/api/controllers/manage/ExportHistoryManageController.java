package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.ExportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.services.exporthistory.ExportHistoryService;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(ExportHistoryManageController.PATH)
@Tag(name = "Export History", description = "Quản lý lịch sử Export File")
public class ExportHistoryManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/export-histories";

    private final ExportHistoryService exportHistoryService;

    @GetMapping
    @Operation(summary = "Lấy danh sách lịch sử Export File")
    public ResponseEntity<PageResponse<ExportHistoryResponse>> list(@RequestParam Integer currentPage,
                                                                    @RequestParam Integer pageSize,
                                                                    @RequestParam(required = false) String fileName,
                                                                    @RequestParam(required = false) List<Integer> types,
                                                                    @RequestParam(required = false) List<Integer> statuses,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date createdAtFrom,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date createdAtTo) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return AppResponse.success(exportHistoryService.list(pageable, fileName, types, statuses, createdAtFrom, createdAtTo));
    }

    @PostMapping
    @Operation(summary = "Yêu cầu export file")
    public ResponseEntity<BaseResponse<ExportHistoryResponse>> create(Authentication authentication,
                                                                      @Valid @ModelAttribute ExportHistoryRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(exportHistoryService.create(request, operatorId));
    }

}
