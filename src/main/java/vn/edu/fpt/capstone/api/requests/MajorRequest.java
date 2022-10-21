package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MajorRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    private String code;

    @NotBlank(message = "Thiếu thông tin ngành học!")
    private String name;

    private Integer level;

    @NotNull(message = "Thiếu parentId")
    @Min(value = 0, message = "Giá trị id không hợp lệ")
    private Long parentId;

    private Boolean enabled;
}
