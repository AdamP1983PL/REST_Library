package com.rest_library.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksBorrowingStatsDto {

    private Long id;
    private List<Long> individualBooksId;
    private Long readerId;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

}
