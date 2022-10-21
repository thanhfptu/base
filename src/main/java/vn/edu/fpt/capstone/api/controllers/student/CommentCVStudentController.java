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
import vn.edu.fpt.capstone.api.responses.CommentResponse;
import vn.edu.fpt.capstone.api.services.comment.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommentCVStudentController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Comment CV Student", description = "Sinh viên xem comment CV bản thân")
public class CommentCVStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/comments";

    public final CommentService commentService;

    @GetMapping("/cv/{id}")
    @Operation(summary = "Lấy danh sách comment từ CV id")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getByCVId(@PathVariable("id") Long cvId) {
        BaseResponse<List<CommentResponse>> result = commentService.getByCVId(cvId);
        return AppResponse.success(result);
    }

    @GetMapping("/count/{id}")
    @Operation(summary = "Đếm comment chưa resolve từ CV id")
    public ResponseEntity<BaseResponse<Integer>> getCountComment(@PathVariable("id") Long cvId) {
        BaseResponse<Integer> result = commentService.countComment(cvId);
        return AppResponse.success(result);
    }
}
