package vn.edu.fpt.capstone.api.controllers.student;

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
import vn.edu.fpt.capstone.api.requests.StudentApplyJobRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.JobResponse;
import vn.edu.fpt.capstone.api.responses.StudentApplyJobResponse;
import vn.edu.fpt.capstone.api.services.applyjob.StudentApplyJobService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(StudentApplyJobController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Student apply job", description = "Sinh viên ứng tuyển công việc ")
public class StudentApplyJobController {
    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/applyJob";

    private final StudentApplyJobService studentApplyJobService;

    @PostMapping
    @Operation(summary = "Sinh viên apply CV vào 1 job")
    public ResponseEntity<BaseResponse<StudentApplyJobResponse>> create(Authentication authentication,
                                                                        @Valid @RequestBody StudentApplyJobRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(studentApplyJobService.create(request, operator));
    }

    @GetMapping("/cv/{id}")
    @Operation(summary = "Lấy danh sách job đã apply của 1 cv")
    public ResponseEntity<BaseResponse<List<JobResponse>>> listJobsAppliedByCV(@PathVariable("id") Long id) {
        return AppResponse.success(studentApplyJobService.listJobsAppliedByCV(id));
    }

    @DeleteMapping
    @Operation(summary = "Hủy apply job")
    public ResponseEntity<BaseResponse<String>> delete(@Valid @RequestBody StudentApplyJobRequest request) {
        return AppResponse.success(studentApplyJobService.delete(request));
    }
}
