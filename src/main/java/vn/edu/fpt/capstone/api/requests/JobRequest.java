package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JobRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;

    @NotNull(message = "Thiếu thông tin companyId")
    private String title;

    @NotNull(message = "Thiếu thông tin description")
    private String description;

    @NotNull(message = "Thiếu thông tin requirement")
    private String requirement;

    private String benefit;

    @Min(value = 1, message = "Giá trị companyId không hợp lệ")
    private Integer numberRecruit;

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "Thiếu thông tin companyId")
    @Min(value = 1, message = "Giá trị companyId không hợp lệ")
    private Long companyId;

    @JsonDeserialize(contentUsing = LongDeserializer.class)
    private List<Long> majorIds;

    @NotNull(message = "Thiếu thông tin publishDate")
    private Date publishDate;

    private Date expiredDate;

    @JsonDeserialize(using = LongDeserializer.class)
    @Min(value = 1, message = "id semester không hợp lệ!")
    private Long semesterId;

    @JsonDeserialize(using = LongDeserializer.class)
    @Min(value = 1, message = "id region không hợp lệ!")
    private Long regionId;

    @NotNull(message = "Thiếu thông tin salary")
    private String salary;

    private String imgUrl;

    private Boolean isVisible;

    private Boolean isActive;

    private Boolean isFeatured;
}
