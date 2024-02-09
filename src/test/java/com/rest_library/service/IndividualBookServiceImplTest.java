package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.IndividualBookMapper;
import com.rest_library.mapper.IndividualBookPostMapper;
import com.rest_library.repository.IndividualBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndividualBookServiceImplTest {

    private Title title1;
    private Title title2;
    private TitleDto titleDto1;
    private TitleDto titleDto2;
    private IndividualBook individualBook1;
    private IndividualBook individualBook2;
    private IndividualBookDto individualBookDto1;
    private IndividualBookDto individualBookDto2;
    private IndividualBookPostDto individualBookPostDto1;
    private IndividualBookPostDto individualBookPostDto2;

    @Mock
    private IndividualBookRepository individualBookRepository;

    @Mock
    private IndividualBookMapper individualBookMapper;

    @Mock
    private IndividualBookPostMapper individualBookPostMapper;

    @InjectMocks
    private IndividualBookServiceImpl individualBookServiceImpl;

    @BeforeEach()
    void initialise() {
        title1 = Title.builder()
                .id(1L)
                .bookTitle("test book title")
                .author("test author")
                .publicationYear(2024)
                .build();

        title2 = Title.builder()
                .id(2L)
                .bookTitle("test book title2")
                .author("test author2")
                .publicationYear(2024)
                .build();

        titleDto1 = TitleDto.builder()
                .id(1L)
                .bookTitle("test book title")
                .author("test author")
                .build();

        titleDto2 = TitleDto.builder()
                .id(1L)
                .bookTitle("test book title2")
                .author("test author2")
                .build();

        individualBook1 = IndividualBook.builder()
                .id(11L)
                .title(title1)
                .status(Status.AVAILABLE)
                .build();

        individualBook2 = IndividualBook.builder()
                .id(22L)
                .title(title2)
                .status(Status.AVAILABLE)
                .build();

        individualBookDto1 = IndividualBookDto.builder()
                .id(11L)
                .individualBookTitle("test book title")
                .status(Status.AVAILABLE)
                .build();

        individualBookDto2 = IndividualBookDto.builder()
                .id(22L)
                .individualBookTitle("test book title2")
                .status(Status.AVAILABLE)
                .build();

        individualBookPostDto1 = IndividualBookPostDto.builder()
                .id(11L)
                .title(title1)
                .status(Status.AVAILABLE)
                .build();

        individualBookPostDto2 = IndividualBookPostDto.builder()
                .id(22L)
                .title(title2)
                .status(Status.AVAILABLE)
                .build();
    }

    @AfterEach()
    void cleanUp() {
        individualBookRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing findAllIndividualBooks() method - positive scenario (valid input).")
    public void givenIndividualBooksList_whenFindAllIndividualBooks_thenReturnListOfIndividualBooks() {
        // given
        given(individualBookMapper.mapToIndividualBookDto(individualBook1)).willReturn(individualBookDto1);
        given(individualBookMapper.mapToIndividualBookDto(individualBook2)).willReturn(individualBookDto2);
        given(individualBookRepository.findAll()).willReturn(List.of(individualBook1, individualBook2));

        // when
        List<IndividualBookDto> testList = individualBookServiceImpl.findAllIndividualBooks();

        // then
        assertAll(
                () -> assertNotNull(testList),
                () -> assertEquals(2, testList.size()),
                () -> assertEquals(11L, testList.get(0).getId()),
                () -> assertEquals("test book title", testList.get(0).getIndividualBookTitle()),
                () -> assertEquals(Status.AVAILABLE, testList.get(0).getStatus())
        );
    }

    @Test
    @DisplayName("Testing findAllIndividualBooks() method - negative scenario (empty List)")
    public void givenEmptyIndividualBooksList_whenFindAllIndividualBooks_thenReturnEmptyList() {
        // given
        given(individualBookRepository.findAll()).willReturn(List.of());

        // when
        List<IndividualBookDto> emptyTestList = individualBookServiceImpl.findAllIndividualBooks();

        // then
        assertAll(
                () -> assertTrue(emptyTestList.isEmpty()),
                () -> assertEquals(0, emptyTestList.size())
        );
    }

    @Test
    @DisplayName("Testing findIndividualBoolById(Long id) method - positive scenario (valid input)")
    public void givenIndividualBooksList_whenFindIndividualBookById_thenReturnIndividualBookObject() {
        // given
        Long id = individualBook1.getId();
        given(individualBookRepository.findById(id)).willReturn(Optional.ofNullable(individualBook1));
        given(individualBookMapper.mapToIndividualBookDto(individualBook1)).willReturn(individualBookDto1);

        // when
        IndividualBookDto testIndividualBookDto = individualBookServiceImpl.findIndividualBookById(id);

        // then
        assertAll(
                () -> assertNotNull(testIndividualBookDto),
                () -> assertEquals(individualBookDto1, testIndividualBookDto),
                () -> assertEquals("test book title", testIndividualBookDto.getIndividualBookTitle())
        );
    }

    @Test
    @DisplayName("Testing findIndividualBoolById(Long id) method that throws ResourceNotFoundException")
    public void givenEmptyIndividualBookList_whenFindIndividualBookById_thenThrowResourceNotFoundException() {
        // given
        Long id = individualBook1.getId();
        given(individualBookRepository.findById(id)).willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            individualBookServiceImpl.findIndividualBookById(id);
        });
        verify(individualBookRepository, times(1)).findById(anyLong());
        verify(individualBookMapper, never()).mapToIndividualBookDto(individualBook1);
    }




}






































