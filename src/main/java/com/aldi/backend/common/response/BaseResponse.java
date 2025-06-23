package com.aldi.backend.common.response;

import com.aldi.backend.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString

public class BaseResponse<T> {

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final T body;

    public BaseResponse(Boolean isSuccess, String code, String message, T body) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.body = body;
    }
    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<>(true, "200", null, body);
    }

    public static <T> BaseResponse<T> failure(BaseErrorCode errorCode) {
        return new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> BaseResponse<T> failure(BaseErrorCode errorCode, T body  ) {
        return new BaseResponse<>(false, errorCode.getCode(), errorCode.getMessage() , body);
    }

    // BaseResponse에 아래 메서드 추가
    public static <T> BaseResponse<T> failure(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }




}
