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
import vn.edu.fpt.capstone.api.requests.ImportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.ImportHistoryResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.importhistory.ImportHistoryService;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(ImportHistoryManageController.PATH)
@Tag(name = "Import History", description = "Quản lý lịch sử Import File")
public class ImportHistoryManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/import-histories";

    private final ImportHistoryService importHistoryService;

    @GetMapping
    @Operation(summary = "Lấy danh sách lịch sử Import File")
    public ResponseEntity<PageResponse<ImportHistoryResponse>> list(@RequestParam Integer currentPage,
                                                                    @RequestParam Integer pageSize,
                                                                    @RequestParam(required = false) String fileName,
                                                                    @RequestParam(required = false) List<Integer> types,
                                                                    @RequestParam(required = false) List<Integer> statuses,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date createdAtFrom,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date createdAtTo) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return AppResponse.success(importHistoryService.list(pageable, fileName, types, statuses, createdAtFrom, createdAtTo));
    }

    @PostMapping
    @Operation(summary = "Tải lên file để Import")
    public ResponseEntity<BaseResponse<ImportHistoryResponse>> create(Authentication authentication,
                                                                      @Valid @ModelAttribute ImportHistoryRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Long operatorId = user.getId();
        return AppResponse.success(importHistoryService.create(request, operatorId));
    }

}
