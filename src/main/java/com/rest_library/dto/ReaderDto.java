package com.rest_library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate startingDate;

}
