package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import com.rest_library.repository.TitleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IndividualBookMapperTest {
    Title testTitle;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    IndividualBookMapper individualBookMapper;

    @BeforeAll
    void initialize() {
        testTitle = Title.builder()
                .id(101L)
                .bookTitle("Test title")
                .author("test author")
                .publicationYear(2024)
                .build();
    }

    @AfterEach
    void cleanUp() {
        titleRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing mapToIndividualBook() method.")
    void givenIndividualBookDto_whenMapToIndividualBook_thenReturnIndividualBookEntity() {
        // given
        Title savedTitle = titleRepository.save(testTitle);
        IndividualBookDto testIndividualBookDto = IndividualBookDto.builder()
                .id(202L)
                .individualBookTitle(testTitle.getBookTitle())
                .status(Status.AVAILABLE)
                .build();

        // when
        IndividualBook mappedActualBook = individualBookMapper.mapToIndividualBook(testIndividualBookDto);

        // then
        assertAll(
                () -> assertNotNull(mappedActualBook),
                () -> assertEquals(202L, mappedActualBook.getId()),
                () -> assertEquals(savedTitle.getId(), mappedActualBook.getTitle().getId()),
                () -> assertEquals("test author", mappedActualBook.getTitle().getAuthor()),
                () -> assertEquals(2024, mappedActualBook.getTitle().getPublicationYear()),
                () -> assertEquals(Status.AVAILABLE, mappedActualBook.getStatus())
        );
    }

    @Test
    @DisplayName("Testing mapToIndividualBookDto() method.")
    void givenIndividualBookEntity_whenMapToIndividualBookDto_thenReturnIndividualBookDto() {
        // given
        Title testTitle2 = Title.builder()
                .id(101L)
                .bookTitle("Test title2")
                .author("test author2")
                .publicationYear(2024)
                .build();

        Title savedTitle2 = titleRepository.save(testTitle2);

        IndividualBook testIndividualBook = IndividualBook.builder()
                .id(303L)
                .title(testTitle2)
                .status(Status.PROHIBITED)
                .build();

        // when
        IndividualBookDto mappedActualBookDto = individualBookMapper.mapToIndividualBookDto(testIndividualBook);

        // then
        assertAll(
                () -> assertNotNull(mappedActualBookDto),
                () -> assertEquals(303L, mappedActualBookDto.getId()),
                () -> assertEquals(savedTitle2.getId(), individualBookMapper.mapIndividualBookTitleToTitle(
                        mappedActualBookDto.getIndividualBookTitle()).getId()),
                () -> assertEquals("test author2", (individualBookMapper.mapIndividualBookTitleToTitle(
                        mappedActualBookDto.getIndividualBookTitle())).getAuthor()),
                () -> assertEquals(2024, (individualBookMapper.mapIndividualBookTitleToTitle(
                        mappedActualBookDto.getIndividualBookTitle())).getPublicationYear()),
                () -> assertEquals(Status.PROHIBITED, mappedActualBookDto.getStatus())
        );
    }

    @Test
    @DisplayName("Testing mapIndividualBookTitleToTitleObject() method.")
    public void givenSavedTitle_whenMapBookTitleToTitleClass_thenReturnTitleObject() {
        // given
        Title savedTitle = titleRepository.save(testTitle);

        // when
        Title actualTitle = individualBookMapper.mapIndividualBookTitleToTitle("Test title");

        // then
        assertNotNull(actualTitle);
        assertEquals("Test title", actualTitle.getBookTitle());
    }

}
