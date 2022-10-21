package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OTPRequest {

    @NotNull(message = "Request ID không được để trống!")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long requestId;

}
