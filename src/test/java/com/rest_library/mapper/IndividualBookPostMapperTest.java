package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IndividualBookPostMapperTest {

    IndividualBook individualBook;
    IndividualBookPostDto individualBookPostDto;
    Title testTitle;

    @Autowired
    IndividualBookPostMapper individualBookPostMapper;

    @BeforeEach
    void initialise() {
        testTitle = Title.builder()
                .id(202L)
                .bookTitle("title one")
                .author("suthor one")
                .publicationYear(2024)
                .build();

        individualBook = IndividualBook.builder()
                .id(2L)
                .title(testTitle)
                .status(Status.AVAILABLE)
                .build();

        individualBookPostDto = IndividualBookPostDto.builder()
                .id(2L)
                .title(testTitle)
                .status(Status.AVAILABLE)
                .build();
    }

    @Test
    @DisplayName("Testing mapToIndividualBook(IndividualBookPostDto individualBookPostDto) method.")
    public void givenIndividualBookPostDtoObject_whenIndividualBookPostMapper_thenReturnIndividualBookObject() {
        // given

        // when
        IndividualBook mappedIndividualBook = individualBookPostMapper.mapToIndividualBook(individualBookPostDto);

        // then
        assertAll(
                () -> assertNotNull(mappedIndividualBook),
                () -> assertEquals(2L, mappedIndividualBook.getId()),
                () -> assertEquals("title one", individualBookPostDto.getTitle().getBookTitle()),
                () -> assertEquals(Status.AVAILABLE, individualBookPostDto.getStatus())
        );
    }

    @Test
    @DisplayName("Testing mapToIndividualBookPostDto(IndividualBook individualBook) method.")
    public void givenIndividualBookObject_whenMapToIndividualBookPostDto_thenReturnIndividualBookPostDtoObject() {
        // given

        // when
        IndividualBookPostDto mappedIndividualBook = individualBookPostMapper.mapToIndividualBookPostDto(individualBook);

        // then
        assertAll(
                () -> assertNotNull(mappedIndividualBook),
                () -> assertEquals(2L, mappedIndividualBook.getId()),
                () -> assertEquals(testTitle, mappedIndividualBook.getTitle()),
                () -> assertEquals(Status.AVAILABLE, mappedIndividualBook.getStatus())
        );
    }

}
