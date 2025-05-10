package com.consultoriomedico.presentation.advice;

import com.consultoriomedico.service.exception.APIResponse;
import com.consultoriomedico.service.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GeneralException {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> resourceNotFoundException(APIException e) {


        String message = e.getMessage();
        APIResponse response = new APIResponse(message,false);
        return new ResponseEntity<>(response
                , HttpStatus.BAD_REQUEST);
    }
}
