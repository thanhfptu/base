package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.dto.FinalEvaluationDTO;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.finalevaluation.FinalEvaluationService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(FinalEvaluationManageController.PATH)
@Tag(name = "Final evaluation", description = "Quản lý Đánh giá cuối kỳ")
public class FinalEvaluationManageController {
    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/evaluation/final";
    private final FinalEvaluationService service;

    @GetMapping
    @Operation(summary = "danh sách đánh giá giữa kỳ")
    ResponseEntity<PageResponse<FinalEvaluationDTO>> list(@RequestParam Integer currentPage,
                                                          @RequestParam Integer pageSize,
                                                          @RequestParam(required = false) String studentName,
                                                          @RequestParam(required = false) String rollNumber,
                                                          @RequestParam(required = false) Long companyId,
                                                          @RequestParam(required = false) Long semesterId,
                                                          @RequestParam(required = false) String companyName,
                                                          @RequestParam(required = false) Date startDate,
                                                          @RequestParam(required = false) Date endDate,
                                                          @RequestParam(required = false) String staffCode,
                                                          @RequestParam(required = false) Long companyContactId,
                                                          @RequestParam(required = false) String contactName,
                                                          @RequestParam(required = false) Integer status) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<FinalEvaluationDTO> response = service.list(pageable,
                studentName,
                rollNumber,
                companyId,
                semesterId,
                companyName,
                startDate,
                endDate,
                staffCode,
                companyContactId,
                contactName,
                status);
        return AppResponse.success(response);
    }
}
