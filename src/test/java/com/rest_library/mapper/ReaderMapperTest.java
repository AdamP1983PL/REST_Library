package com.rest_library.mapper;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReaderMapperTest {

    ReaderMapper readerMapper = new ReaderMapper();

    @Test
    @DisplayName("Testing mapToReader() method.")
    void givenReaderDto_whenMapToReader_thenReturnReaderEntity() {
        // given
        ReaderDto testReaderDto = ReaderDto.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(2024, 1, 1))
                .build();

        // when
        Reader mappedReader = readerMapper.mapToReader(testReaderDto);

        // then
        assertAll(
                () -> assertNotNull(mappedReader),
                () -> assertEquals(1L, mappedReader.getId()),
                () -> assertEquals("test@test.com", mappedReader.getEmail()),
                () -> assertEquals("test first name", mappedReader.getFirstName()),
                () -> assertEquals("test last name", mappedReader.getLastName()),
                () -> assertEquals(LocalDate.of(2024, 1, 1), mappedReader.getStartingDate())
        );
    }

    @Test
    @DisplayName("Testing mapToReaderDto() method.")
    void givenReaderEntity_whenMapToReaderDto_thenReturnReaderDto() {
        // given
        Reader testReader = Reader.builder()
                .id(2L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(2022, 10, 10))
                .build();

        // when
        ReaderDto mappedReaderDto = readerMapper.mapToReaderDto(testReader);

        // then
        assertAll(
                () -> assertNotNull(mappedReaderDto),
                () -> assertEquals("test@test.com", mappedReaderDto.getEmail()),
                () -> assertEquals("test first name", mappedReaderDto.getFirstName()),
                () -> assertEquals("test last name", mappedReaderDto.getLastName()),
                () -> assertEquals(LocalDate.of(2022, 10, 10), mappedReaderDto.getStartingDate())
        );
    }

}
