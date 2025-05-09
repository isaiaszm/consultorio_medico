package com.consultoriomedico.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralException {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> resourceNotFoundException(IllegalStateException e) {
        Map<String, String> response = new HashMap<>();


            response.put("error", e.getMessage());

        return new ResponseEntity<>(response
                , HttpStatus.BAD_REQUEST);
    }
}
