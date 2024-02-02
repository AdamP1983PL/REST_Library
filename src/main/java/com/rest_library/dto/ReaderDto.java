package com.rest_library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate startingDate;

}
