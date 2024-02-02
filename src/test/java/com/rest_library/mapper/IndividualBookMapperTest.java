package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IndividualBookMapperTest {

    IndividualBookMapper individualBookMapper = new IndividualBookMapper();
    Title testTitle = Title.builder()
            .id(101L)
            .author("test author")
            .publicationYear(2024)
            .build();

    @Test
    @DisplayName("Testing mapToIndividualBook() method.")
    void givenIndividualBookDto_whenMapToIndividualBook_thenReturnIndividualBookEntity() {
        // given
        IndividualBookDto testIndividualBookDto = IndividualBookDto.builder()
                .id(202L)
                .title(testTitle)
                .status(Status.AVAILABLE)
                .build();

        // when
        IndividualBook mappedActualBook = individualBookMapper.mapToIndividualBook(testIndividualBookDto);

        // then
        assertAll(
                () -> assertNotNull(mappedActualBook),
                () -> assertEquals(202L, mappedActualBook.getId()),
                () -> assertEquals(101L, mappedActualBook.getTitle().getId()),
                () -> assertEquals("test author", mappedActualBook.getTitle().getAuthor()),
                () -> assertEquals(2024, mappedActualBook.getTitle().getPublicationYear()),
                () -> assertEquals(Status.AVAILABLE, mappedActualBook.getStatus())
        );
    }

    @Test
    @DisplayName("Testing mapToIndividualBookDto() method.")
    void givenIndividualBookEntity_whenMapToIndividualBookDto_thenReturnIndividualBookDto() {
        // given
        IndividualBook testIndividualBook = IndividualBook.builder()
                .id(303L)
                .title(testTitle)
                .status(Status.PROHIBITED)
                .build();

        // when
        IndividualBookDto mappedActualBookDto = individualBookMapper.mapToIndividualBookDto(testIndividualBook);

        // then
        assertAll(
                () -> assertNotNull(mappedActualBookDto),
                () -> assertEquals(303L, mappedActualBookDto.getId()),
                () -> assertEquals(101L, mappedActualBookDto.getTitle().getId()),
                () -> assertEquals("test author", mappedActualBookDto.getTitle().getAuthor()),
                () -> assertEquals(2024, mappedActualBookDto.getTitle().getPublicationYear()),
                () -> assertEquals(Status.PROHIBITED, mappedActualBookDto.getStatus())
        );
    }

}
