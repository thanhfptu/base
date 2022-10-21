package vn.edu.fpt.capstone.api.controllers;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
        Sentry.captureException(e);
        log.error("Đã có lỗi xảy ra, chi tiết: {}", e.getMessage());
        return AppResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Đã có lỗi xảy ra. Vui lòng liên hệ IT để được hỗ trợ.");
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleBindException(BindException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, Objects.requireNonNull(FieldError::getDefaultMessage)));
        return AppResponse.error(HttpStatus.BAD_REQUEST, "Request không hợp lệ!", errors);
    }

}
