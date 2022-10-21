package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegionRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    private Integer level;

    @NotBlank(message = "Tên kì region không hợp lệ!")
    private String name;

    @Min(value = 0, message = "Giá trị parentId không hợp lệ")
    private Long parentId;

}
