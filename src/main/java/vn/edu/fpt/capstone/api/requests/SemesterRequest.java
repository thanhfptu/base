package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
@Setter
public class SemesterRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotBlank(message = "Tên kì học không hợp lệ!")
    private String name;

    @NotBlank(message = "Mã kì học không được để trống!")
    private String code;

    @NotNull(message = "Thiếu thông tin năm học!")
    @Positive(message = "Thông tin năm học không hợp lệ!")
    private Integer year;

    @NotNull(message = "Thiếu thông tin thời gian bắt đầu!")
    private Date startDate;

    @NotNull(message = "Thiếu thông tin ngày kết thúc!")
    private Date endDate;

    @NotNull(message = "Thiếu thông tin isActive!")
    private Boolean isActive;

}
