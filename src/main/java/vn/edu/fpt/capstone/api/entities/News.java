package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.requests.NewsRequest;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = News.TABLE_NAME)
public class News extends BaseEntity {

    public static final String TABLE_NAME = "news";

    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "is_visible")
    private Boolean isVisible;
    @Column(name = "is_featured")
    private Boolean isFeatured;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "publish_date")
    private Date publishDate;

    public static News of(NewsRequest request) {
        Date publishDate = DateUtils.at(DateUtils.toLocalDate(request.getPublishDate()), LocalTime.MIN);
        return News.builder()
                .title(request.getTitle().strip())
                .content(request.getContent().strip())
                .isVisible(request.getIsVisible())
                .isFeatured(request.getIsFeatured())
                .thumbnail(request.getThumbnail().strip())
                .shortDescription(request.getShortDescription().strip())
                .publishDate(publishDate)
                .isVisible(publishDate.compareTo(DateUtils.now()) <= 0)
                .build();
    }

    public static News of(NewsRequest request, Long createBy) {
        News build = News.of(request);
        build.setCreatedAt(DateUtils.now());
        build.setCreatedBy(createBy);
        return build;
    }
}
