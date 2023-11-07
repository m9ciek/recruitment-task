package com.recruitment.task.api;

import com.recruitment.task.exception.UserExceptionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerAdviceTest {

    private final UserControllerAdvice controllerAdvice = new UserControllerAdvice();

    @Test
    public void givenHttpClientErrorExceptionNotFound_whenHandled_thenReturnResponseNotFoundWithMessage() {
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        ResponseEntity<UserExceptionResponse> responseEntity = controllerAdvice.handleHttpClientErrorException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        UserExceptionResponse response = responseEntity.getBody();

        assert response != null;
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getErrorCode());
        assertEquals(exception.getMessage(), response.getMessage());
    }

    @Test
    public void givenException_whenHandled_thenReturnResponseBadRequestWithMessage() {
        Exception exception = new Exception("Test exception message");

        ResponseEntity<UserExceptionResponse> responseEntity = controllerAdvice.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        UserExceptionResponse response = responseEntity.getBody();

        assert response != null;
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getErrorCode());
        assertEquals(exception.getMessage(), response.getMessage());
    }
}