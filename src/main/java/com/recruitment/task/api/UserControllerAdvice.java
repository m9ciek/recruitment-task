package com.recruitment.task.api;

import com.recruitment.task.exception.UserExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@RestControllerAdvice
public class UserControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerAdvice.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<UserExceptionResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        logger.warn(e.getMessage());

        UserExceptionResponse response = new UserExceptionResponse();

        response.setErrorCode(e.getStatusCode().value());
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<UserExceptionResponse> handleException(Exception e) {
        logger.warn(e.getMessage());

        UserExceptionResponse response = new UserExceptionResponse();

        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}