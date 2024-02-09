package com.rest_library.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TitleAlreadyExistsException extends RuntimeException {
    private String message;

    public TitleAlreadyExistsException(String message) {
        super(message);
        log.info("====>>>> TitleAlreadyExistsException() execution");
    }

}
