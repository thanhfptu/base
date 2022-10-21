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
import vn.edu.fpt.capstone.api.requests.FinalEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.FinalEvaluationResponse;
import vn.edu.fpt.capstone.api.services.finalevaluation.FinalEvaluationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(FinalEvaluationController.PATH)
@Tag(name = "Final evaluation", description = "tạo, update Đánh giá cuối kỳ")
public class FinalEvaluationController {
    public static final String PATH = "/evaluation" + AppConfig.V1_PATH + "/final";
    private final FinalEvaluationService service;

    @GetMapping("/{id}")
    @Operation
    ResponseEntity<BaseResponse<FinalEvaluationResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<FinalEvaluationResponse> response = service.get(id);
        return AppResponse.success(response);
    }


    @PostMapping
    @Operation
    ResponseEntity<BaseResponse<FinalEvaluationResponse>> create(@Valid @RequestBody FinalEvaluationRequest request) {
        return AppResponse.success(service.save(request));
    }

    @PutMapping
    @Operation
    ResponseEntity<BaseResponse<FinalEvaluationResponse>> update(@Valid @RequestBody FinalEvaluationRequest request) {
        return AppResponse.success(service.save(request));
    }
}
