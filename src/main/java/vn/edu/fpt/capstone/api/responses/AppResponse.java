package vn.edu.fpt.capstone.api.responses;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppResponse {

    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, BaseResponse<T> data) {
        return ResponseEntity.status(statusCode).body(data);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, String message, T data) {
        return ResponseEntity.status(statusCode).body(BaseResponse.error(message, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus statusCode, String message) {
        return ResponseEntity.status(statusCode).body(BaseResponse.error(message));
    }

}
