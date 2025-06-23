package com.aldi.backend.common.exception;


public interface BaseErrorCode{
    public String getCode();
    public String getMessage();
    public int getHttpStatus();
}
