package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Comment;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private UserResponse author;
    private UserCVResponse cv;
    private String content;
    private Boolean isResolved;
    private Boolean isDeleted;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .author(Objects.isNull(comment.getAuthorId()) ? null : UserResponse.builder().id(comment.getAuthorId()).build())
                .cv(Objects.isNull(comment.getCvId()) ? null : UserCVResponse.builder().id(comment.getCvId()).build())
                .content(comment.getContent())
                .isResolved(comment.getIsResolved())
                .isDeleted(comment.getIsDeleted())
                .build();
    }

    public static CommentResponse of(Comment comment, UserResponse author, UserCVResponse cvResponse) {
        CommentResponse build = CommentResponse.of(comment);
        build.setAuthor(author);
        build.setCv(cvResponse);
        return build;
    }

    public static CommentResponse of(Comment comment, UserResponse author) {
        CommentResponse build = CommentResponse.of(comment);
        build.setAuthor(author);
        return build;
    }
}
