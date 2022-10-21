package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OTPAuthenticationRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "Request ID không được để trống!")
    private Long requestId;

    @NotEmpty(message = "Mã OTP không được để trống!")
    private String otp;

}
