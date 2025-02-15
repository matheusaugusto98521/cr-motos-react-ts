package com.example.cr_motos_backend.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationNotFoundException extends Exception {
    public InformationNotFoundException(String message) {
        super(message);
    }
}
