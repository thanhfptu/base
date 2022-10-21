package vn.edu.fpt.capstone.api.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import vn.edu.fpt.capstone.api.deserializers.LongDeserializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserRequest {

    private Long id;

    @NotBlank(message = "Thiếu thông tin Full Name!")
    private String fullName;

    @NotBlank(message = "Thiếu thông tin rollNumber!")
    private String rollNumber;

    @NotNull(message = "Thiếu thông tin Gender!")
    private Integer gender;

    private Integer status;

    private Date dateOfBirth;

    @JsonDeserialize(using = LongDeserializer.class)
    private Long majorId;

    @NotNull(message = "Thiếu thông tin Campus!")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long campusId;

    @NotBlank(message = "Thiếu thông tin FPT Education Email!")
    @Email(message = "Email không hợp lệ!")
    private String eduEmail;

    private String personalEmail;

    private String phoneNumber;

    private String address;

    private Integer ojtStatus;

    @JsonDeserialize(contentUsing = LongDeserializer.class)
    private List<Long> roles;

}
