package com.HarshG.journalApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex){
        return new ResponseEntity<>(
                Map.of("message","Invalid request body"),
        HttpStatus.BAD_REQUEST
        );
    }

}
