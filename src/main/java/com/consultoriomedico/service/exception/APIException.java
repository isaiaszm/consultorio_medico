package com.consultoriomedico.service.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class APIException extends RuntimeException{

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
