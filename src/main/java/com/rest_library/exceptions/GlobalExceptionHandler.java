package com.rest_library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("USER_NOT_FOUND_EXCEPTION")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        // todo use ResourceNotFoundException lesson 88, 89
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistException exception,
                                                                          WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("READER_EMAIL_ALREADY_EXISTS")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        // todo use EmailAlreadyExistsException lesson 88, 89
    }

    @ExceptionHandler(TitleAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleTitleAlreadyExistsException(TitleAlreadyExistsException exception,
                                                                          WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("TITLE_ALREADY_EXISTS")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        // todo use TitleAlreadyExistsException lesson 88, 89
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception,
                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode("INTERNAL_SERVER_ERROR")
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        // todo use handleException lesson 89
    }

}
