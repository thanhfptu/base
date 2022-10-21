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
public class CampusRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotBlank(message = "Thiếu thông tin Tên Campus!")
    private String name;

    private String address;

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "Thiếu thông tin Region Id!")
    @Min(value = 0, message = "Region Id không hợp lệ!")
    private Long regionId;

    private String phoneNumber;

    private String email;

    private Integer status;

}
