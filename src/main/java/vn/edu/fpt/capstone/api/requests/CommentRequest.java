package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "Thiếu cvId!")
    private Long cvId;

    @NotBlank(message = "thiếu thông tin content!")
    private String content;

}
