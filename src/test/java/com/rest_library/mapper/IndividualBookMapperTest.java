package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndividualBookMapperTest {

    IndividualBookMapper individualBookMapper = new IndividualBookMapper();
    IdToEntityMapperToBeDiscarded mapper = new IdToEntityMapperToBeDiscarded();
    Title tempTitle = Title.builder()
            .id(1L).bookTitle("test title").author("test author").publicationYear(2024).build();
//    @Test
//    void mapToIndividualBook() {
//        // given
//        IndividualBookDto tempBookDto = IndividualBookDto.builder()
//                .id(2L).title_id(tempTitle.getId()).status(Status.IN_CIRCULATION)
//                .build();
//
//        // when
//        IndividualBook expectedIndividualBook = individualBookMapper.mapToIndividualBook(tempBookDto);
//
//        // when
//        assertEquals(expectedIndividualBook.getId(), 2L);
//        assertEquals(expectedIndividualBook.getTitle(), "test title");
//        assertEquals(expectedIndividualBook.getStatus(), Status.IN_CIRCULATION);
//
//
//    }
//

    @Test
    void mapToIndividualBookDto() {
        // given
        IndividualBook tempBook = IndividualBook.builder()
                .id(1L).title(tempTitle).status(Status.AVAILABLE).build();

        // when
        IndividualBookDto expectedIndividualBookDto = individualBookMapper.mapToIndividualBookDto(tempBook);

        // then
        assertEquals(expectedIndividualBookDto.getId(), 1L);
        assertEquals(expectedIndividualBookDto.getStatus(), Status.AVAILABLE);
        assertEquals(expectedIndividualBookDto.getTitle_id(), 1L);
    }
}
