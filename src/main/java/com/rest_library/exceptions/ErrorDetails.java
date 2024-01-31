package com.rest_library.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;

    // todo lesson 87

}
