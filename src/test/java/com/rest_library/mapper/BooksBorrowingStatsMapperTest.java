package com.rest_library.mapper;

import com.rest_library.dto.BooksBorrowingStatsDto;
import com.rest_library.entity.BooksBorrowingStats;
import com.rest_library.entity.Reader;
import com.rest_library.repository.IndividualBookRepository;
import com.rest_library.repository.ReaderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BooksBorrowingStatsMapperTest {
    Reader testReader;
    Reader savedReader;

    @Autowired
    IndividualBookRepository individualBookRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BooksBorrowingStatsMapper booksBorrowingStatsMapper;

    @BeforeEach
    void initialise() {
        testReader = Reader.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.now())
                .booksBorrowingStatsList(Collections.emptyList())
                .build();

        savedReader = readerRepository.save(testReader);
    }

    @AfterEach
    void cleanUp() {
        readerRepository.deleteAll();
        individualBookRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing mapToBooksBorrowingStats() method.")
    void givenBooksBorrowingStatsDto_whenMapToBooksBorrowingStats_thenReturnBooksBorrowingStatsEntity() {
        // given
        BooksBorrowingStatsDto testStatsDto = BooksBorrowingStatsDto.builder()
                .id(1L)
                .individualBooksId(Collections.emptyList())
                .readerId(savedReader.getId())
                .borrowingDate(LocalDate.of(2024, 1, 2))
                .returnDate(LocalDate.of(2024, 2, 2))
                .build();

        // when
        BooksBorrowingStats mappedTestStats = booksBorrowingStatsMapper.mapToBooksBorrowingStats(testStatsDto);

        // then
        assertAll(
                () -> assertNotNull(mappedTestStats),
                () -> assertEquals(1L, mappedTestStats.getId()),
                () -> assertEquals(savedReader.getId(), mappedTestStats.getReader().getId()),
                () -> assertEquals("test@test.com", mappedTestStats.getReader().getEmail()),
                () -> assertEquals("test first name", mappedTestStats.getReader().getFirstName()),
                () -> assertEquals("test last name", mappedTestStats.getReader().getLastName()),
                () -> assertEquals(LocalDate.of(2024, 1, 2), mappedTestStats.getBorrowingDate()),
                () -> assertEquals(LocalDate.of(2024, 2, 2), mappedTestStats.getReturnDate())
        );
    }

    @Test
    @DisplayName("Testing mapToBooksBorrowingStatsDto() method.")
    void givenBooksBorrowingStatsEntity_whenMapToBooksBorrowingStatsDto_thenReturnBooksBorrowingStatsDto() {
        // given
        BooksBorrowingStats testStats = BooksBorrowingStats.builder()
                .id(2L)
                .individualBooks(Collections.emptyList())
                .reader(testReader)
                .borrowingDate(LocalDate.of(2024, 3, 3))
                .returnDate(LocalDate.of(2024, 4, 4))
                .build();

        // when
        BooksBorrowingStatsDto mappedTestStatsDto = booksBorrowingStatsMapper.mapToBooksBorrowingStatsDto(testStats);

        // then
        assertAll(
                () -> assertNotNull(mappedTestStatsDto),
                () -> assertEquals(2L, mappedTestStatsDto.getId()),
                () -> assertEquals(testReader.getId(), mappedTestStatsDto.getReaderId()),
                () -> assertEquals(1L, mappedTestStatsDto.getReaderId()),
                () -> assertEquals(LocalDate.of(2024, 3, 3), mappedTestStatsDto.getBorrowingDate()),
                () -> assertEquals(LocalDate.of(2024, 4, 4), mappedTestStatsDto.getReturnDate())
        );

    }
}
