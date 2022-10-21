package vn.edu.fpt.capstone.api.services.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.fpt.capstone.api.constants.CVStatus;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Comment;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.UserCV;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.CommentRepository;
import vn.edu.fpt.capstone.api.repositories.UserCVRepository;
import vn.edu.fpt.capstone.api.repositories.UserRepository;
import vn.edu.fpt.capstone.api.requests.CommentRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CommentResponse;
import vn.edu.fpt.capstone.api.responses.UserResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service(CommentService.SERVICE_NAME)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final UserCVRepository userCVRepository;

    @Override
    public BaseResponse<List<CommentResponse>> getByCVId(Long cvId) {
        try {
            List<Comment> comments = commentRepository.findByCvId(cvId);

            if (CollectionUtils.isEmpty(comments)) {
                return BaseResponse.success(null);
            }

            List<CommentResponse> responses = new ArrayList<>();

            for (Comment comment : comments) {
                if (!comment.getIsDeleted()) {
                    User author = userRepository.findById(comment.getAuthorId())
                            .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Student với id %s!", comment.getAuthorId())));
                    CommentResponse response = CommentResponse.of(comment, UserResponse.of(author));
                    responses.add(response);
                }
            }
            return BaseResponse.success(responses);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CommentResponse> updateResolve(Long id, User operator) {
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy comment với id %s", id)));

            if (comment.getIsDeleted()) {
                return BaseResponse.success(null);
            }

            Long cvId = comment.getCvId();

            UserCV userCV = userCVRepository.findById(cvId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cv với id %s", cvId)));

            comment.setIsResolved(true);
            comment.setModifiedAt(DateUtils.now());
            comment.setModifiedBy(operator.getId());

            comment = commentRepository.save(comment);

            userCV.setStatus(CVStatus.APPROVED);
            userCV.setModifiedAt(DateUtils.now());
            userCV.setModifiedBy(operator.getId());
            userCVRepository.save(userCV);

            CommentResponse response = CommentResponse.of(comment);
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CommentResponse> create(CommentRequest request, User operator) {
        try {
            Long cvId = request.getCvId();

            UserCV userCV = userCVRepository.findById(cvId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cv với id %s", cvId)));

            Comment comment = commentRepository.save(Comment.of(request, operator.getId()));

            userCV.setStatus(CVStatus.FAILED);
            userCV.setModifiedAt(DateUtils.now());
            userCV.setModifiedBy(operator.getId());
            userCVRepository.save(userCV);

            return BaseResponse.success(CommentResponse.of(comment));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<CommentResponse> delete(Long id, User operator) {
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy comment với id %s", id)));

            Long cvId = comment.getCvId();

            UserCV userCV = userCVRepository.findById(cvId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy cv với id %s", cvId)));

            comment.setIsDeleted(true);
            comment.setModifiedAt(DateUtils.now());
            comment.setModifiedBy(operator.getId());

            comment = commentRepository.save(comment);

            if (userCV.getStatus().equals(CVStatus.FAILED)) {
                userCV.setStatus(CVStatus.FAILED);
                userCV.setModifiedAt(DateUtils.now());
                userCV.setModifiedBy(operator.getId());
            }
            userCVRepository.save(userCV);

            CommentResponse response = CommentResponse.of(comment);
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<Integer> countComment(Long cvId) {
        BaseSpecification<Comment> specification = new BaseSpecification<>();
        specification.add(new SearchCriteria("isResolved", false, SearchOperation.EQUAL));
        specification.add(new SearchCriteria("cvId", cvId, SearchOperation.EQUAL));
        specification.add(new SearchCriteria("isDeleted", false, SearchOperation.EQUAL));
        List<Comment> comments = commentRepository.findAll(specification);
        return BaseResponse.success(comments.size());
    }
}
