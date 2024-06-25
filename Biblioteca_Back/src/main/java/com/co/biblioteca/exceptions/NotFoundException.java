package com.co.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public static final long serialVersionID = 1L;

    public NotFoundException(String mensaje) {
        super(mensaje);
    }
}
