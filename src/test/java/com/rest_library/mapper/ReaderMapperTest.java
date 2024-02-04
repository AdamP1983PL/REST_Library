package com.rest_library.mapper;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReaderMapperTest {
    ReaderDto testReaderDto;
    ReaderDto testReaderDto2;
    Reader testReader;
    Reader testReader2;
    ReaderMapper readerMapper = new ReaderMapper();


    @BeforeEach()
    public void initialise() {
        testReader = Reader.builder()
                .id(2L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(2022, 10, 10))
                .build();

        testReader2 = Reader.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("test first name2")
                .lastName("test last name2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();

        testReaderDto = ReaderDto.builder()
                .id(1L)
                .email("testDto@test.com")
                .firstName("test dto first name")
                .lastName("test dto last name")
                .startingDate(LocalDate.of(2024, 1, 1))
                .build();

        testReaderDto2 = ReaderDto.builder()
                .id(1L)
                .email("testDto2@test.com")
                .firstName("test dto first name2")
                .lastName("test dto last name2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();

    }

    @Test
    @DisplayName("Testing mapToReader() method.")
    void givenReaderDto_whenMapToReader_thenReturnReaderEntity() {
        // given

        // when
        Reader mappedReader = readerMapper.mapToReader(testReaderDto);

        // then
        assertAll(
                () -> assertNotNull(mappedReader),
                () -> assertEquals(1L, mappedReader.getId()),
                () -> assertEquals("testDto@test.com", mappedReader.getEmail()),
                () -> assertEquals("test dto first name", mappedReader.getFirstName()),
                () -> assertEquals("test dto last name", mappedReader.getLastName()),
                () -> assertEquals(LocalDate.of(2024, 1, 1), mappedReader.getStartingDate())
        );
    }

    @Test
    @DisplayName("Testing mapToReaderDto() method.")
    void givenReaderEntity_whenMapToReaderDto_thenReturnReaderDto() {
        // given

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

    @Test
    @DisplayName("Testing mapToReaderDtoList() method.")
    public void givenReadersList_whenMapToReaderDtoList_thenReturnReadersDtoList() {
        // given
        List<Reader> readers = new ArrayList<>(List.of(testReader, testReader2));

        // when
        List<ReaderDto> mappedReaderDtoList = readerMapper.mapToReaderDtoList(readers);

        // then
        assertAll(
                () -> assertEquals(readers.size(), mappedReaderDtoList.size()),
                () -> assertEquals("test@test.com", mappedReaderDtoList.get(0).getEmail()),
                () -> assertEquals("test2@test.com", mappedReaderDtoList.get(1).getEmail()),
                () -> assertEquals("test first name", mappedReaderDtoList.get(0).getFirstName()),
                () -> assertEquals("test first name2", mappedReaderDtoList.get(1).getFirstName())
        );
    }

}
