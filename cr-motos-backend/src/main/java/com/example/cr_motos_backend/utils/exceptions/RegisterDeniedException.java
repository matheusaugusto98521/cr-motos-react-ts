package com.example.cr_motos_backend.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterDeniedException extends Exception {
    public RegisterDeniedException(String message) {
        super(message);
    }
}
