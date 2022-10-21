package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class RoleRequest {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    private String code;
    private String name;

}
