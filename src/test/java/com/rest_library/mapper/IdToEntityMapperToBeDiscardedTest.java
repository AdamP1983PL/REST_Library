package com.rest_library.mapper;

import com.rest_library.entity.Title;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IdToEntityMapperToBeDiscardedTest {

    @Test
    void givenId_whenMapIdToEntity_thenReturnEntity() {
        // given
        IdToEntityMapperToBeDiscarded idToEntityMapper = new IdToEntityMapperToBeDiscarded();
        Title title = Title.builder()
                .id(1L)
                .bookTitle("Test Title")
                .author("Test Author")
                .publicationYear(2024)
                .individualBooks(Collections.emptyList())
                .build();

//        Title title = new Title(1L, "Test Title", "Test Author", 2024, Collections.emptyList());

        // when
        Title testObject = (Title) idToEntityMapper.mapIdToEntity(1L, Title.class);

        // then
        assertNotNull(testObject);
        assertEquals(1L, testObject.getId());
//        assertEquals("Test Title", testObject.getBookTitle());
//        assertEquals("Test Author", testObject.getAuthor());
//        assertEquals(2024, testObject.getPublicationYear());

    }



}
