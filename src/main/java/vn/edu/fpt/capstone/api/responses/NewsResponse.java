package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.edu.fpt.capstone.api.entities.News;
import vn.edu.fpt.capstone.api.entities.User;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class NewsResponse {
    private Long id;
    private UserResponse author;
    private String title;
    private String content;
    private Boolean isVisible;
    private Boolean isFeatured;
    private String thumbnail;
    private String shortDescription;
    private Date publishDate;

    public static NewsResponse of(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .author(UserResponse.builder().id(news.getAuthorId()).build())
                .title(news.getTitle())
                .content(news.getContent())
                .isVisible(news.getIsVisible())
                .isFeatured(news.getIsFeatured())
                .thumbnail(news.getThumbnail())
                .shortDescription(news.getShortDescription())
                .publishDate(news.getPublishDate())
                .build();
    }

    public static NewsResponse of(News news, UserResponse author) {
        NewsResponse build = NewsResponse.of(news);
        build.setAuthor(author);
        return build;
    }

}
