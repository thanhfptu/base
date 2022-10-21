package vn.edu.fpt.capstone.api.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestAddCompanyContactRequest {

    private Long id;
    @NotBlank(message = "Thiếu thông tin Email!")
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotBlank(message = "Thiếu thông tin Số điện thoại!")
    private String phone;
    @NotBlank(message = "Thiếu thông tin Họ tên!")
    private String name;
}
