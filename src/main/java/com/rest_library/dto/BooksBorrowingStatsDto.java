package com.rest_library.dto;

import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Reader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksBorrowingStatsDto {

    private Long id;
    private List<Long> individualBooksId = new ArrayList<>();
    private Long readerId;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

}
