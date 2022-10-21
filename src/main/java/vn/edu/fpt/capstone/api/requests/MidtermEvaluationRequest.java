package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class MidtermEvaluationRequest {
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

    @NotNull(message = "Thiếu thông tin companyId")
    private Integer comment;

    private String note;

    @NotNull(message = "Thiếu thông tin contactId!")
    private Long contactId;

}
