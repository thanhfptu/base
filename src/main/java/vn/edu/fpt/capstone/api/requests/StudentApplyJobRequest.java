package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudentApplyJobRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "thiếu jobId!")
    private Long jobId;

    @JsonDeserialize(using = LongDeserializer.class)
    @NotNull(message = "thiếu cvId!")
    private Long cvId;
    
}
