package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.NONE)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setMessage("Thành công");
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> success() {
        return success(null);
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> error(String message) {
        return error(message, null);
    }

}
