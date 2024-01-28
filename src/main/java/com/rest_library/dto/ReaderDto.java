package com.rest_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDto {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate startingDate;

}
