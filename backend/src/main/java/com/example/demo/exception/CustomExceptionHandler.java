package com.example.demo.exception;

import com.example.demo.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.errorCode.getHttpStatusCode()).body(new ErrorResponse(e.errorCode.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {

        boolean isMissingParams = false;

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            // 필수 입력값 누락 (@NotNull, @NotEmpty, @NotBlank)
            if (error.getCode().equals("NotNull") || error.getCode().equals("NotEmpty") || error.getCode().equals("NotBlank")) {
                isMissingParams = true;
            }
        }

        if (isMissingParams) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("MISSING_PARAMETER", "필수 파라미터를 입력하세요"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("INVALID_PARAMETER", "파라미터 값이 잘못되었습니다"));
        }
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(ErrorCode.NOT_FOUND.getHttpStatusCode())
                .body(new ErrorResponse(ErrorCode.NOT_FOUND.getCode(), "요청 URL을 찾을 수 없습니다"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(ErrorCode.NOT_FOUND.getHttpStatusCode())
                .body(new ErrorResponse(ErrorCode.NOT_FOUND.getCode(), "요청 URL에 대한 HTTP 메소드가 올바르지 않습니다"));
    }

    @ExceptionHandler(Exception.class) // 정의해두지 않은 Exception 은 일단 500으로 처리
    public ResponseEntity<?> handleAllException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getLocalizedMessage()));
    }

}
