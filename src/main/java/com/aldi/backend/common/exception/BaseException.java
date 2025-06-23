package com.aldi.backend.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final String message;
}
