package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class IdsRequest {

    @NotEmpty(message = "Danh sách Id không hợp lệ!")
    @JsonDeserialize(contentUsing = LongDeserializer.class)
    private List<Long> ids;

}
