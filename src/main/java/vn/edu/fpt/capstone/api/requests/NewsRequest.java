package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class NewsRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotBlank(message = "Thiếu tiêu đề!")
    private String title;

    @NotBlank(message = "Thiếu nội dung!")
    private String content;

    private Boolean isVisible;

    @NotNull(message = "Thiếu thông tin Featured!")
    private Boolean isFeatured;

    @NotBlank(message = "Thiếu thumbnail!")
    private String thumbnail;

    @NotBlank(message = "Thiếu shortDescription!")
    private String shortDescription;

    private Date publishDate;
}
