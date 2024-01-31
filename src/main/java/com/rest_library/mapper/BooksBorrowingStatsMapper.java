package com.rest_library.mapper;

import com.rest_library.dto.BooksBorrowingStatsDto;
import com.rest_library.entity.BooksBorrowingStats;
import org.springframework.stereotype.Component;

@Component
public class BooksBorrowingStatsMapper {

    public BooksBorrowingStats mapToBooksBorrowingStats(BooksBorrowingStatsDto booksBorrowingStatsDto) {
        return BooksBorrowingStats.builder()
                .id(booksBorrowingStatsDto.getId())
                .individualBooks(booksBorrowingStatsDto.getIndividualBooksList())
                .readersList(booksBorrowingStatsDto.getReadersList())
                .borrowingDate(booksBorrowingStatsDto.getBorrowingDate())
                .returnDate(booksBorrowingStatsDto.getReturnDate())
                .build();
    }

    public BooksBorrowingStatsDto mapToBooksBorrowingStatsDto(BooksBorrowingStats booksBorrowingStats) {
        return BooksBorrowingStatsDto.builder()
                .id(booksBorrowingStats.getId())
                .individualBooksList(booksBorrowingStats.getIndividualBooks())
                .readersList(booksBorrowingStats.getReadersList())
                .borrowingDate(booksBorrowingStats.getBorrowingDate())
                .returnDate(booksBorrowingStats.getReturnDate())
                .build();
    }

}
