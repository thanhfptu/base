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
public class FinalEvaluationRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotNull(message = "Thiếu thông tin ngày bắt đầu")
    private Date startDate;

    @NotNull(message = "Thiếu thông tin ngày kết thúc")
    private Date endDate;

    @NotNull(message = "Thiếu thông tin thời gian làm việc")
    private Integer duration;

    private String staffCode;

    private String division;

    @NotBlank(message = "Thiếu thông tin nhận xét")
    private String comment;

    private String allowance;

    @NotNull(message = "Thiếu thông tin điểm kiến thức!")
    private Float majorPoint;

    @NotNull(message = "Thiếu thông tin điểm thái độ!")
    private Float attitudePoint;

    @NotNull(message = "Thiếu thông tin điểm kỹ năng mềm!")
    private Float softSkillPoint;

    @NotNull(message = "Thiếu thông tin điểm tổng!")
    private Float finalPoint;

    @NotNull(message = "Thiếu thông tin contactId!")
    private Long contactId;
}
