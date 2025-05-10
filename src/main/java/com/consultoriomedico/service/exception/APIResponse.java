package com.consultoriomedico.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class APIResponse {
    private String message;
    private boolean status;

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}