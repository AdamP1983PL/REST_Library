package com.rest_library.mapper;

import com.rest_library.dto.BooksBorrowingStatsDto;
import com.rest_library.entity.BooksBorrowingStats;
import com.rest_library.entity.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksBorrowingStatsMapperTest {

    BooksBorrowingStatsMapper booksBorrowingStatsMapper = new BooksBorrowingStatsMapper();
    Reader testReader = Reader.builder()
            .id(1L)
            .email("test@test.com")
            .firstName("test first name")
            .lastName("test last name")
            .startingDate(LocalDate.now())
            .booksBorrowingStatsList(Collections.emptyList())
            .build();


    @Test
    @DisplayName("Testing mapToBooksBorrowingStats() method.")
    void givenBooksBorrowingStatsDto_whenMapToBooksBorrowingStats_thenReturnBooksBorrowingStatsEntity() {
        // given
        BooksBorrowingStatsDto testStatsDto = BooksBorrowingStatsDto.builder()
                .id(1L)
                .individualBooksList(Collections.emptyList())
                .readersList(List.of(testReader))
                .borrowingDate(LocalDate.of(2024, 1, 2))
                .returnDate(LocalDate.of(2024, 2, 2))
                .build();

        // when
        BooksBorrowingStats mappedTestStats = booksBorrowingStatsMapper.mapToBooksBorrowingStats(testStatsDto);

        // then
        assertAll(
                () -> assertNotNull(mappedTestStats),
                () -> assertEquals(1L, mappedTestStats.getId()),
                () -> assertEquals(1L, mappedTestStats.getReadersList().get(0).getId()),
                () -> assertEquals("test@test.com", mappedTestStats.getReadersList().get(0).getEmail()),
                () -> assertEquals("test first name", mappedTestStats.getReadersList().get(0).getFirstName()),
                () -> assertEquals("test last name", mappedTestStats.getReadersList().get(0).getLastName()),
                () -> assertEquals(2024, mappedTestStats.getBorrowingDate().getYear()),
                () -> assertEquals(1, mappedTestStats.getBorrowingDate().getMonthValue()),
                () -> assertEquals(2, mappedTestStats.getBorrowingDate().getDayOfMonth()),
                () -> assertEquals(2024, mappedTestStats.getReturnDate().getYear()),
                () -> assertEquals(2, mappedTestStats.getReturnDate().getMonthValue()),
                () -> assertEquals(2, mappedTestStats.getReturnDate().getDayOfMonth())
        );
    }

    @Test
    @DisplayName("Testing mapToBooksBorrowingStatsDto() method.")
    void givenBooksBorrowingStatsEntity_whenMapToBooksBorrowingStatsDto_thenReturnBooksBorrowingStatsDto() {
        // given
        BooksBorrowingStats testStats = BooksBorrowingStats.builder()
                .id(2L)
                .individualBooks(Collections.emptyList())
                .readersList(List.of(testReader))
                .borrowingDate(LocalDate.of(2024, 3, 3))
                .returnDate(LocalDate.of(2024, 4, 4))
                .build();

        // when
        BooksBorrowingStatsDto mappedTestStatsDto = booksBorrowingStatsMapper.mapToBooksBorrowingStatsDto(testStats);

        // then
        assertAll(
                () -> assertNotNull(mappedTestStatsDto),
                () -> assertEquals(2L, mappedTestStatsDto.getId()),
                () -> assertEquals(1L, mappedTestStatsDto.getReadersList().get(0).getId()),
                () -> assertEquals("test@test.com", mappedTestStatsDto.getReadersList().get(0).getEmail()),
                () -> assertEquals("test first name", mappedTestStatsDto.getReadersList().get(0).getFirstName()),
                () -> assertEquals("test last name", mappedTestStatsDto.getReadersList().get(0).getLastName()),
                () -> assertEquals(2024, mappedTestStatsDto.getBorrowingDate().getYear()),
                () -> assertEquals(3, mappedTestStatsDto.getBorrowingDate().getMonthValue()),
                () -> assertEquals(3, mappedTestStatsDto.getBorrowingDate().getDayOfMonth()),
                () -> assertEquals(2024, mappedTestStatsDto.getReturnDate().getYear()),
                () -> assertEquals(4, mappedTestStatsDto.getReturnDate().getMonthValue()),
                () -> assertEquals(4, mappedTestStatsDto.getReturnDate().getDayOfMonth())
        );

    }
}
