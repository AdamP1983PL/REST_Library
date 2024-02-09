package com.rest_library.service;

import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BooksBorrowingStatsServiceImplTest {

    private Title testTitle;
    private Title testTitle2;
    private TitleDto testTitleDto;
    private TitleDto testTitleDto2;
    private IndividualBook individualBook1;
    private IndividualBook individualBook2;
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

    // todo givenTitleOfTheBook_whenBorrowAvailableBookByTitle_thenThrowResourceNotFoundException()

//    @Test
//    @DisplayName("Testing returnBooks(List<IndividualBookPostDto> individualBooks) method.")
//    public void givenListOfIndividualBookPostDtoList_whenReturnBooks_thenAllIndividualBookChangeStatusToAvailable() {
//        // given
//        List<IndividualBook> libraryBooks = List.of(individualBook1, individualBook2);
//        List<IndividualBookPostDto> returnedBooks = List.of(individualBookPostDto2);
//        when(individualBookPostMapper.mapToIndividualBook(individualBookPostDto2)).thenReturn(individualBook2);
//        when(individualBookPostMapper.mapToIndividualBookPostDto(individualBook2)).thenReturn(individualBookPostDto2);
//        when(individualBookRepository.findAll()).thenReturn(libraryBooks);
//
//        // when
//        List<IndividualBookPostDto> testedIndividualBookPostDto = booksBorrowingStatsServiceImpl.returnBooks(returnedBooks);
//
//        // then
//        assertAll(
//                () -> assertNotNull(testedIndividualBookPostDto),
//                () -> assertEquals(Status.AVAILABLE, testedIndividualBookPostDto.get(0).getStatus())
//        );
//
//    }

}
