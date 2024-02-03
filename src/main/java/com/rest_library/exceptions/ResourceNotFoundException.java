package com.rest_library.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s not found with: %s : %s", resourceName, fieldName, fieldValue));
        log.info("====>>>> ResourceNotFoundException() execution");
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;

    }

    // todo use ResourceNotFoundException lesson 86!!!!!!!!!!!!!!!
    // todo add CustomExceptions
    // todo add GlobalExceptionHandler
}
