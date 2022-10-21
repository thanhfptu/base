package vn.edu.fpt.capstone.api.services.comment;

import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.requests.CommentRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CommentResponse;

import java.util.List;

public interface CommentService {
    String SERVICE_NAME = "CommentService";

    BaseResponse<List<CommentResponse>> getByCVId(Long cvId);

    BaseResponse<CommentResponse> updateResolve(Long id, User operator);

    BaseResponse<CommentResponse> create(CommentRequest request, User operator);

    BaseResponse<CommentResponse> delete(Long id, User operator);

    BaseResponse<Integer> countComment(Long cvId);

}