package com.rest_library.mapper;

import com.rest_library.dto.BooksBorrowingStatsDto;
import com.rest_library.entity.BooksBorrowingStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BooksBorrowingStatsMapper {

    public BooksBorrowingStats mapToBooksBorrowingStats(BooksBorrowingStatsDto booksBorrowingStatsDto) {
        return BooksBorrowingStats.builder()
                .id(booksBorrowingStatsDto.getId())
                .individualBooks(booksBorrowingStatsDto.getIndividualBooksList())
                .reader(booksBorrowingStatsDto.getReader())
                .borrowingDate(booksBorrowingStatsDto.getBorrowingDate())
                .returnDate(booksBorrowingStatsDto.getReturnDate())
                .build();

    }

    public BooksBorrowingStatsDto mapToBooksBorrowingStatsDto(BooksBorrowingStats booksBorrowingStats) {
        return BooksBorrowingStatsDto.builder()
                .id(booksBorrowingStats.getId())
                .individualBooksList(booksBorrowingStats.getIndividualBooks())
                .reader(booksBorrowingStats.getReader())
                .borrowingDate(booksBorrowingStats.getBorrowingDate())
                .returnDate(booksBorrowingStats.getReturnDate())
                .build();
    }

}
