package com.rest_library.repository;

import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IndividualBookRepositoryTest {
    IndividualBook individualBook1;
    IndividualBook individualBook2;
    IndividualBook individualBook3;

    @Autowired
    private IndividualBookRepository individualBookRepository;

    @BeforeEach
    void initialise() {
        individualBook1 = IndividualBook.builder()
                .id(1L)
                .title(new Title(1L,
                        "Three body problem",
                        "Cixin Liu",
                        2022,
                        Collections.emptyList()))
                .status(Status.AVAILABLE)
                .build();

        individualBook2 = IndividualBook.builder()
                .id(2L)
                .title(new Title(1L,
                        "Three body problem",
                        "Cixin Liu",
                        2022,
                        Collections.emptyList()))
                .status(Status.IN_CIRCULATION)
                .build();

        individualBook3 = IndividualBook.builder()
                .id(2L)
                .title(new Title(1L,
                        "Dark forest",
                        "Cixin Liu",
                        2022,
                        Collections.emptyList()))
                .status(Status.AVAILABLE)
                .build();
    }

    @AfterEach
    void cleanUp() {
        individualBookRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing findByTitleBookTitleAndStatus() method.")
    public void givenIndividualBooksList_whenFindByTitleBookTitleAndStatus_thenReturnListOfOneBook() {
        // given
        individualBookRepository.save(individualBook1);
        individualBookRepository.save(individualBook2);
        List<IndividualBook> individualBooks = new ArrayList<>(List.of(individualBook1, individualBook2));

        // when
        List<IndividualBook> filteredList = individualBookRepository
                .findByTitleBookTitleAndStatus(individualBook1.getTitle().getBookTitle(), Status.AVAILABLE)
                .stream().toList();

        System.out.println("\t\t\t ====>>>> info: " + filteredList.get(0).getTitle().getBookTitle());
        System.out.println("\t\t\t ====>>>> info: " + filteredList.get(0).getTitle().getAuthor());
        System.out.println("\t\t\t ====>>>> info: " + filteredList.get(0).getStatus());

        // then
        assertAll(
                () -> assertNotNull(filteredList),
                () -> assertEquals(1, filteredList.size()),
                () -> assertEquals("Three body problem", filteredList.get(0).getTitle().getBookTitle()),
                () -> assertEquals("Cixin Liu", filteredList.get(0).getTitle().getAuthor()),
                () -> assertEquals(Status.AVAILABLE, filteredList.get(0).getStatus())
        );
    }

    @Test
    @DisplayName("Testing findByTitle() method.")
    public void given_when_then() {
        // given
        individualBookRepository.save(individualBook1);
        individualBookRepository.save(individualBook3);
        List<IndividualBook> individualBooks = new ArrayList<>(List.of(individualBook1, individualBook3));

        // when
        List<IndividualBook> filteredList = individualBookRepository
                .findByTitleBookTitle(individualBook1.getTitle().getBookTitle()).stream().toList();

        System.out.println("\t\t\t ====>>>> info: " + filteredList.get(0).getTitle().getBookTitle());

        // then
        assertAll(
                () -> assertNotNull(filteredList),
                () -> assertEquals(1, filteredList.size()),
                () -> assertEquals("Three body problem", filteredList.get(0).getTitle().getBookTitle())
        );
    }

}
