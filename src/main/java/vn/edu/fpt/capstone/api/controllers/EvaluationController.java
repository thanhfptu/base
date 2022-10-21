package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.dto.EvaluationDTO;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.services.evaluation.EvaluationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping(EvaluationController.PATH)
@Tag(name = "Evaluation", description = "")
public class EvaluationController {
    public static final String PATH = "/evaluation" + AppConfig.V1_PATH;
    private final EvaluationService service;

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<BaseResponse<List<EvaluationDTO>>> get(@PathVariable("id")Long contactId){
        BaseResponse<List<EvaluationDTO>> response = service.getByContactId(contactId);
        return AppResponse.success(response);
    }
}
