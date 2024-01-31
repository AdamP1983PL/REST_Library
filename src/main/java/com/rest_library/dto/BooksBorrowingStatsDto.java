package com.rest_library.dto;

import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Reader;
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
    private List<IndividualBook> individualBooksList;
    private List<Reader> readersList;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

}
