package vn.edu.fpt.capstone.api.controllers.student;

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
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CVPreviewResponse;
import vn.edu.fpt.capstone.api.services.cvpreview.CVPreviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CVPreviewStudentController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "CV preview student", description = "preview CV")
public class CVPreviewStudentController {
    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/cv-preview";

    private final CVPreviewService previewService;

    @GetMapping("/{id}")
    @Operation(summary = "lấy url thumbnail của cv bằng cv_id")
    public ResponseEntity<BaseResponse<List<CVPreviewResponse>>> get(@PathVariable("id") Long id) {
        return AppResponse.success(previewService.getByCVId(id));
    }
}
