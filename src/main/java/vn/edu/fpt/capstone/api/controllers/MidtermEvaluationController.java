package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.MidtermEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.MidtermEvaluationResponse;
import vn.edu.fpt.capstone.api.services.midtermevaluation.MidtermEvaluationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(MidtermEvaluationController.PATH)
@Tag(name = "Midterm evaluation", description = "tạo, update Đánh giá giữa kỳ")
public class MidtermEvaluationController {
    public static final String PATH = "/evaluation" + AppConfig.V1_PATH + "/midterm";
    private final MidtermEvaluationService service;

    @GetMapping("/{id}")
    @Operation
    ResponseEntity<BaseResponse<MidtermEvaluationResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<MidtermEvaluationResponse> response = service.get(id);
        return AppResponse.success(response);
    }

    @PostMapping
    @Operation
    ResponseEntity<BaseResponse<MidtermEvaluationResponse>> create(@RequestBody MidtermEvaluationRequest request) {
        return AppResponse.success(service.save(request));
    }

    @PutMapping
    @Operation
    ResponseEntity<BaseResponse<MidtermEvaluationResponse>> update(@Valid @RequestBody MidtermEvaluationRequest request) {
        return AppResponse.success(service.save(request));
    }
}
