package com.rest_library.service;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.exceptions.EmailAlreadyExistException;
import com.rest_library.exceptions.ResourceNotFoundException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    @DisplayName("Testing saveReader(ReaderDto readerDto) method.")
    public void givenReaderDtoObject_whenSaveReader_thenReturnSavedReaderObject() {
        // given
        given(readerRepository.findReaderByEmail(testReader.getEmail())).willReturn(Optional.empty());
        given(readerMapper.mapToReader(testReaderDto)).willReturn(testReader);
        given(readerMapper.mapToReaderDto(any())).willReturn(testReaderDto);
        given(readerServiceImpl.saveReader(testReaderDto))
                .willReturn(testReaderDto);

        /*stubbing for mapToReaderDto using any() as the argument matcher,
        this ensures that the mapToReaderDto method is considered valid regardless of the argument
        passed to it during the test.*/

        // when
        ReaderDto savedReaderDto = readerServiceImpl.saveReader(testReaderDto);

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
    @DisplayName("Testing saveReader(ReaderDto readerDto) method that throws EmailAlreadyExistsException.")
    public void givenExistingReaderEmail_whenSaveReader_thenThrowsEmailAlreadyExistsException() {
        // given
        given(readerRepository.findReaderByEmail(testReader.getEmail())).willReturn(Optional.ofNullable(testReader));

        // when, then
        assertThrows(EmailAlreadyExistException.class, () -> {
            readerServiceImpl.saveReader(testReaderDto);
        });
        verify(readerRepository, never()).save(any(Reader.class));
    }

    @Test
    @DisplayName("Testing findAllReaders() method - positive scenario (valid input).")
    public void givenReadersList_whenFindAllReaders_thenReturnAllReaders() {
        // given
        given(readerMapper.mapToReaderDto(testReader)).willReturn(testReaderDto);
        given(readerMapper.mapToReaderDto(testReader2)).willReturn(testReaderDto2);
        given(readerRepository.findAll()).willReturn(List.of(testReader, testReader2));

        // when
        List<ReaderDto> allReadersDto = readerServiceImpl.findAllReaders();

        // then
        assertAll(
                () -> assertFalse(allReadersDto.isEmpty()),
                () -> assertNotNull(allReadersDto),
                () -> assertEquals(2, allReadersDto.size()),
                () -> assertEquals("test first name", allReadersDto.get(0).getFirstName()),
                () -> assertEquals("test2@test.com", allReadersDto.get(1).getEmail())
        );
    }

    @Test
    @DisplayName("Testing findAllReaders() method - negative scenario (empty List).")
    public void givenEmptyReadersList_whenFindAllReaders_thenReturnEmptyReadersList() {
        // given
        given(readerRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<ReaderDto> emptyList = readerServiceImpl.findAllReaders();

        // then
        assertAll(
                () -> assertTrue(emptyList.isEmpty()),
                () -> assertEquals(0, emptyList.size())
        );
    }

    @Test
    @DisplayName("Testing findReader(Long id) method - positive scenario (valid input).")
    public void givenReadersObjectList_whenFindReader_thenReturnReaderDtoObject() {
        // given
        given(readerMapper.mapToReaderDto(testReader)).willReturn(testReaderDto);
        given(readerRepository.findById(testReader.getId())).willReturn(Optional.ofNullable(testReader));

        // when
        ReaderDto actualReader = readerServiceImpl.findReader(testReaderDto.getId());

        // then
        assertAll(
                () -> assertNotNull(actualReader),
                () -> assertEquals("test@test.com", actualReader.getEmail()),
                () -> assertEquals("test first name", actualReader.getFirstName())
        );
    }

    @Test
    @DisplayName("Testing findReader(Long id) that throws ResourceNotFoundException.")
    public void givenEmptyReadersList_whenFindReader_thenThrowsResourceNotFoundException() {
        // given
        given(readerRepository.findById(testReader.getId())).willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            readerServiceImpl.findReader(testReaderDto.getId());
        });
        verify(readerRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Testing updateReader(ReaderDto readerDto, Long id) method.")
    public void givenReaderObject_whenUpdateReaderDto_thenReturnUpdatedReaderDtoObject() {
        // given
        Reader updatedReader = Reader.builder()
                .id(1L)
                .email("updated@updated.com")
                .firstName("updated first name")
                .lastName("updated last name")
                .startingDate(LocalDate.now())
                .build();

        ReaderDto updatedReaderDto = ReaderDto.builder()
                .id(1L)
                .email("updated@updated.com")
                .firstName("updated first name")
                .lastName("updated last name")
                .startingDate(LocalDate.now())
                .build();

        given(readerRepository.save(testReader)).willReturn(testReader);
        given(readerRepository.findById(testReader.getId())).willReturn(Optional.ofNullable(testReader));
        given(readerRepository.save(testReader)).willReturn(updatedReader);
        given(readerMapper.mapToReaderDto(any())).willReturn(updatedReaderDto);

        // when
        ReaderDto actualReader = readerServiceImpl.updateReader(testReaderDto, testReaderDto.getId());

        // then
        assertAll(
                () -> assertNotNull(actualReader),
                () -> assertEquals("updated@updated.com", actualReader.getEmail())
        );
    }

    @Test
    @DisplayName("Testing deleteReader() method - positive scenario (valid input).")
    public void givenReadersObjectList_whenDeleteReader_thenReturnEmptyReadersList() {
        // given
        List<Reader> readers = List.of(testReader);
        given(readerRepository.findById(testReader.getId())).willReturn(Optional.ofNullable(testReader));

        // when
        readerServiceImpl.deleteReader(testReader.getId());

        // then
        verify(readerRepository, times(1)).deleteById(testReader.getId());
    }

    @Test
    @DisplayName("Testing deleteReader() that throws ResourceNotFoundException")
    public void givenEmptyReaderList_whenDeleteReader_thenThrowsResourceNotFoundException() {
        // given
        given(readerRepository.findById(testReader.getId())).willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            readerServiceImpl.deleteReader(testReader.getId());
        });
        verify(readerRepository, times(1)).findById(anyLong());
        verify(readerRepository, never()).deleteById(testReader.getId());
    }

}
