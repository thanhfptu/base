package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCVRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "thiếu studentId!")
    private Long studentId;

    private String url;

    private Integer status;

    private Boolean enabled;
}
