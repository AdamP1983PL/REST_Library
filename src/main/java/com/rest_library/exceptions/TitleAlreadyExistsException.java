package com.rest_library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TitleAlreadyExistsException extends RuntimeException{
    private String message;

    public TitleAlreadyExistsException(String message) {
        super(message);
    }

    // todo use TitleAlreadyExistsException lesson 88

}
