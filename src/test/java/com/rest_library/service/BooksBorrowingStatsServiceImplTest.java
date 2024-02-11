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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksBorrowingStatsServiceImplTest {

    private Title testTitle;
    private Title testTitle2;
    private TitleDto testTitleDto;
    private TitleDto testTitleDto2;
    private IndividualBook individualBook1;
    private IndividualBook individualBook2;
    private IndividualBookDto individualBookDto1;
    private IndividualBookPostDto individualBookPostDto1;
    private IndividualBookPostDto individualBookPostDto2;

    @BeforeEach
    void initialize() {
        testTitle = Title.builder()
                .id(1L)
                .bookTitle("test title 1")
                .author("test author 1")
                .publicationYear(1111)
                .individualBooks(List.of())
                .build();

        testTitleDto = TitleDto.builder()
                .id(1L)
                .bookTitle("test title 1")
                .author("test author 1")
                .build();

        testTitle2 = Title.builder()
                .id(2L)
                .bookTitle("test title 2")
                .author("test author 2")
                .publicationYear(2222)
                .individualBooks(List.of())
                .build();

        testTitleDto2 = TitleDto.builder()
                .id(2L)
                .bookTitle("test title 2")
                .author("test author 2")
                .build();

        individualBook1 = IndividualBook.builder()
                .id(1L)
                .title(testTitle)
                .status(Status.AVAILABLE)
                .build();

        individualBook2 = IndividualBook.builder()
                .id(2L)
                .title(testTitle2)
                .status(Status.AVAILABLE)
                .build();

        individualBookDto1 = IndividualBookDto.builder()
                .id(1L)
                .individualBookTitle(testTitle.getBookTitle())
                .status(Status.IN_CIRCULATION)
                .build();

        individualBookPostDto1 = IndividualBookPostDto.builder()
                .id(1L)
                .title(testTitle)
                .status(Status.IN_CIRCULATION)
                .build();

        individualBookPostDto2 = IndividualBookPostDto.builder()
                .id(2L)
                .title(testTitle2)
                .status(Status.AVAILABLE)
                .build();
    }

    @AfterEach
    void cleanUp() {
        individualBookRepository.deleteAll();
    }

    @Mock
    private IndividualBookRepository individualBookRepository;

    @Mock
    private IndividualBookMapper individualBookMapper;

    @Mock
    private IndividualBookPostMapper individualBookPostMapper;

    @InjectMocks
    private BooksBorrowingStatsServiceImpl booksBorrowingStatsServiceImpl;

    @Test
    @DisplayName("Testing borrowAvailableBooksByTitle(String title) method.")
    public void givenTitleOfTheBook_whenBorrowAvailableBookByTitle_thenBorrowedBookStatusShouldBeInCirculation() {
        // given
        List<IndividualBook> individualBookList = new ArrayList<>(List.of(individualBook1, individualBook2));

        given(individualBookRepository.findFirstByTitleBookTitleAndStatus(testTitle.getBookTitle(), Status.AVAILABLE))
                .willReturn(Optional.ofNullable(individualBookList.get(0)));
        given(individualBookPostMapper.mapToIndividualBookPostDto(individualBook1))
                .willReturn(individualBookPostDto1);

        // when
        IndividualBookPostDto testedIndividualBookPostDto =
                booksBorrowingStatsServiceImpl.borrowAvailableBookByTitle(testTitle.getBookTitle());

        // then
        assertAll(
                () -> assertNotNull(testedIndividualBookPostDto),
                () -> assertEquals("test title 1", testedIndividualBookPostDto.getTitle().getBookTitle()),
                () -> assertEquals(Status.IN_CIRCULATION, testedIndividualBookPostDto.getStatus())
        );
    }

    @Test
    @DisplayName("Testing borrowAvailableBookByTitle() method that throws ResourceNotFoundException.")
    public void givenTitleOfTheBook_whenBorrowAvailableBookByTitle_thenThrowResourceNotFoundException() {
        // given
        String nonExistingTitle = "xxx";
        given(individualBookRepository.findFirstByTitleBookTitleAndStatus(nonExistingTitle, Status.AVAILABLE))
                .willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            booksBorrowingStatsServiceImpl.borrowAvailableBookByTitle(nonExistingTitle);
        });
        verify(individualBookRepository, times(1)).findFirstByTitleBookTitleAndStatus(nonExistingTitle, Status.AVAILABLE);
    }

    @Test
    @DisplayName("Testing returnBooks(List<IndividualBookPostDto> individualBooks) method.")
    public void givenListOfIndividualBookPostDtoList_whenReturnBooks_thenAllIndividualBookChangeStatusToAvailable() {
        // given
        IndividualBookDto returnedBookDto = IndividualBookDto.builder()
                .id(1L)
                .individualBookTitle(testTitle.getBookTitle())
                .status(Status.AVAILABLE)
                .build();

        IndividualBook returnedBook = IndividualBook.builder()
                .id(1L)
                .title(testTitle)
                .status(Status.AVAILABLE)
                .build();

        when(individualBookMapper.mapToIndividualBook(individualBookDto1)).thenReturn(individualBook1);
        when(individualBookMapper.mapToIndividualBookDto(individualBook1)).thenReturn(returnedBookDto);
        when(individualBookRepository.save(individualBook1)).thenReturn(returnedBook);
        when(individualBookRepository.save(individualBook1)).thenReturn(returnedBook);

        // when
        IndividualBookDto returnedIndividualBookDto = booksBorrowingStatsServiceImpl.returnABook(individualBookDto1);

        // then
        assertAll(
                () -> assertNotNull(returnedIndividualBookDto),
                () -> assertEquals(Status.AVAILABLE, returnedIndividualBookDto.getStatus())
        );
    }

}
