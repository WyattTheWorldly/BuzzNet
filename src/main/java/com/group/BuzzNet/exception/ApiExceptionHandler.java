package com.group.BuzzNet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e){
        //1. Create a payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        //2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }
}
