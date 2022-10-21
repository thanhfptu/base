package vn.edu.fpt.capstone.api.controllers.manage;

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
import vn.edu.fpt.capstone.api.requests.CommentRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CommentResponse;
import vn.edu.fpt.capstone.api.services.comment.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CommentManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "CV Comment", description = "Quản lý CV Comment")
public class CommentManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/comments";

    public final CommentService commentService;

    @GetMapping("/cv/{id}")
    @Operation(summary = "Lấy danh sách comment từ CV id")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getByCVId(@PathVariable("id") Long cvId) {
        BaseResponse<List<CommentResponse>> result = commentService.getByCVId(cvId);
        return AppResponse.success(result);
    }

    @PutMapping("/resolve/{id}")
    @Operation(summary = "resolve comment")
    public ResponseEntity<BaseResponse<CommentResponse>> updateResolve(Authentication authentication,
                                                                       @PathVariable("id") Long id) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(commentService.updateResolve(id, operator));
    }

    @PostMapping("")
    @Operation(summary = "resolve comment")
    public ResponseEntity<BaseResponse<CommentResponse>> create(Authentication authentication,
                                                                @Valid @RequestBody CommentRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(commentService.create(request, operator));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa comment bằng id")
    public ResponseEntity<BaseResponse<CommentResponse>> delete(@PathVariable("id") Long id, Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(commentService.delete(id, operator));
    }
}
