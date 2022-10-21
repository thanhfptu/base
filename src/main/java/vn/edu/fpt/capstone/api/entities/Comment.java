package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.CommentRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Comment.TABLE_NAME)
public class Comment extends BaseEntity {

    public static final String TABLE_NAME = "cv_comment";

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "cv_id")
    private Long cvId;

    @Column(name = "content")
    private String content;

    @Column(name = "is_resolved")
    private Boolean isResolved;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public static Comment of(CommentRequest request) {
        return Comment.builder()
                .cvId(request.getCvId())
                .content(request.getContent())
                .isResolved(false)
                .isDeleted(false)
                .build();
    }

    public static Comment of(CommentRequest request, Long createdBy) {
        Comment build = Comment.of(request);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(createdBy);
        return build;
    }
}
