package com.rest_library.mapper;

import com.rest_library.dto.TitleDto;
import com.rest_library.entity.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TitleMapperTest {

    TitleMapper titleMapper = new TitleMapper();

    @Test
    @DisplayName("Testing mapToTitle() method.")
    void givenTitleDto_whenMapToTitle_thenReturnTitleEntity() {
        // given
        TitleDto testTitleDto = TitleDto.builder()
                .id(123L)
                .bookTitle("test title")
                .author("test author")
//                .publicationYear(2024)
                .build();

        // when
        Title mappedTitle = titleMapper.mapToTitle(testTitleDto);

        // then
        assertAll(
                () -> assertNotNull(mappedTitle),
                () -> assertEquals(123L, mappedTitle.getId()),
                () -> assertEquals("test title", mappedTitle.getBookTitle()),
                () -> assertEquals(2024, mappedTitle.getPublicationYear())
        );
    }

    @Test
    @DisplayName("Testing mapToTitleDto() method.")
    void givenTitleEntity_whenMapToTitleDto_thenReturnTitleDto() {
        // given
        Title testTitle = Title.builder()
                .id(321L)
                .bookTitle("test title")
                .author("test author")
                .publicationYear(2022)
                .build();

        // when
        TitleDto mappedTitleDto = titleMapper.mapToTitleDto(testTitle);

        // then
        assertAll(
                () -> assertNotNull(mappedTitleDto),
                () -> assertEquals(321L, mappedTitleDto.getId()),
                () -> assertEquals("test title", mappedTitleDto.getBookTitle()),
                () -> assertEquals("test author", mappedTitleDto.getAuthor())
//                () -> assertEquals(2022, mappedTitleDto.getPublicationYear())
        );
    }

}
