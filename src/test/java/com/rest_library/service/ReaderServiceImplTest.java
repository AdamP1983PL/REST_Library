package com.rest_library.service;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.exceptions.EmailAlreadyExistException;
import com.rest_library.mapper.ReaderMapper;
import com.rest_library.repository.ReaderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReaderServiceImplTest {

    private Reader testReader;
    private Reader testReader2;
    private ReaderDto testReaderDto;
    private ReaderDto testReaderDto2;

    @BeforeEach
    void initialize() {
        testReader = Reader.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(1111, 1, 1))
                .build();

        testReader2 = Reader.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("test first name 2")
                .lastName("test last name 2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();

        testReaderDto = ReaderDto.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(1111, 1, 1))
                .build();

        testReaderDto2 = ReaderDto.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("test first name 2")
                .lastName("test last name 2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();
    }

    @AfterEach
    public void cleanUp() {
        readerRepository.deleteAll();
    }

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private ReaderMapper readerMapper;

    @InjectMocks
    private ReaderServiceImpl readerServiceImpl;

    @Test
    @DisplayName("Testing saveReaderDto() method.")
    public void givenReaderDtoObject_whenSaveReaderDto_thenReturnSavedReaderObject() {
        // given
        given(readerRepository.findReaderByEmail(testReader.getEmail())).willReturn(Optional.empty());
        given(readerMapper.mapToReader(testReaderDto)).willReturn(testReader);
        given(readerMapper.mapToReaderDto(any())).willReturn(testReaderDto);
        given(readerServiceImpl.saveReaderDto(testReaderDto))
                .willReturn(testReaderDto);

        /*stubbing for mapToReaderDto using any() as the argument matcher,
        this ensures that the mapToReaderDto method is considered valid regardless of the argument
        passed to it during the test.*/

        // when
        ReaderDto savedReaderDto = readerServiceImpl.saveReaderDto(testReaderDto);

        // then
        assertNotNull(savedReaderDto);
        assertAll(
                () -> assertEquals(1L, savedReaderDto.getId()),
                () -> assertEquals("test@test.com", savedReaderDto.getEmail()),
                () -> assertEquals("test first name", savedReaderDto.getFirstName()),
                () -> assertEquals("test last name", savedReaderDto.getLastName()),
                () -> assertEquals(LocalDate.of(1111, 1, 1), savedReaderDto.getStartingDate())
        );
    }

    @Test
    @DisplayName("Testing saveReaderDto() method that throws EmailAlreadyExistsException.")
    public void givenExistingReaderEmail_whenSaveReaderDto_thenThrowsEmailAlreadyExistsException() {
        // given
        given(readerRepository.findReaderByEmail(testReader.getEmail())).willReturn(Optional.ofNullable(testReader));

        // when
        assertThrows(EmailAlreadyExistException.class, () -> {
            readerServiceImpl.saveReaderDto(testReaderDto);
        });

        // then
        verify(readerRepository, never()).save(any(Reader.class));
    }


}


