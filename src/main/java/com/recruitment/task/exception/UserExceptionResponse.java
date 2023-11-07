package com.recruitment.task.exception;

import lombok.Data;

@Data
public class UserExceptionResponse {
    private int errorCode;
    private String message;
    private long timestamp;
}
