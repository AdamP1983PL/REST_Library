package com.rest_library.repository;

import com.rest_library.entity.Reader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReaderRepositoryTest {

    @Autowired
    private ReaderRepository readerRepository;
    private Reader testReader;
    private Reader testReader2;

    @BeforeEach
    void initialize() {
        testReader = Reader.builder()
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(1111, 1, 1))
                .build();

        testReader2 = Reader.builder()
                .email("test2@test.com")
                .firstName("test first name 2")
                .lastName("test last name 2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();
    }

    @AfterEach
    public void cleanUp() {
        readerRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing save() method.")
    public void givenReaderObject_whenSave_thenReturnSavedReader() {
        // given
        // when
        Reader savedReader = readerRepository.save(testReader);

        // then
        assertNotNull(savedReader);
        assertAll(
                () -> assertEquals("test@test.com", savedReader.getEmail()),
                () -> assertEquals("test first name", savedReader.getFirstName()),
                () -> assertEquals("test last name", savedReader.getLastName()),
                () -> assertEquals(LocalDate.of(1111, 1, 1), savedReader.getStartingDate()),
                () -> assertTrue(savedReader.getId() > 0)
        );
    }

    @Test
    @DisplayName("Testing findAll() method.")
    public void givenSavedReaderObjects_whenFindAll_thenReturnSavedReadersList() {
        // given
        readerRepository.save(testReader);
        readerRepository.save(testReader2);

        // when
        List<Reader> readers = readerRepository.findAll();

        // then
        assertNotNull(readers);
        assertFalse(readers.isEmpty());
        assertEquals(2, readers.size());
    }

    @Test
    @DisplayName("Testing findById() method.")
    public void givenReaderObject_whenFindById_thenReturnReaderObject() {
        // given
        readerRepository.save(testReader);

        // when
        Reader expectedReader = readerRepository.findById(testReader.getId()).get();

        // then
        assertNotNull(expectedReader);
        assertEquals("test@test.com", expectedReader.getEmail());
    }

    @Test
    @DisplayName("Testing findReaderByEmail() method.")
    public void givenReaderObject_whenFindReaderByEmail_thenReturnReaderObject() {
        // given
        readerRepository.save(testReader);

        // when
        Reader expectedReader = readerRepository.findReaderByEmail(testReader.getEmail()).get();

        // then
        assertNotNull(expectedReader);
        assertEquals("test first name", expectedReader.getFirstName());
    }

    @Test
    @DisplayName("Testing update Reader scenario.")
    public void givenReaderObject_whenUpdateReader_thenReturnUpdatedReader() {
        // given
        readerRepository.save(testReader);

        // when
        Reader savedReader = readerRepository.findById(testReader.getId()).get();
        savedReader.setEmail("updated@updated.com");
        savedReader.setFirstName("updated name");
        savedReader.setLastName("updated last name");
        savedReader.setStartingDate(LocalDate.now());
        Reader updatedReader = readerRepository.save(savedReader);

        // then
        assertNotNull(updatedReader);
        assertAll(
                () -> assertEquals("updated@updated.com", updatedReader.getEmail()),
                () -> assertEquals("updated name", updatedReader.getFirstName()),
                () -> assertEquals("updated last name", updatedReader.getLastName()),
                () -> assertEquals(2024, updatedReader.getStartingDate().getYear())
        );
    }

    @Test
    @DisplayName("Testing deleteById() method.")
    public void given_when_then() {
        // given
        readerRepository.save(testReader);
        readerRepository.save(testReader2);
        List<Reader> initialReadersList = readerRepository.findAll();

        // when
        readerRepository.deleteById(testReader.getId());
        List<Reader> readersListAfterDeletion = readerRepository.findAll();

        // then
        assertEquals(1, readersListAfterDeletion.size());
        assertEquals("test2@test.com", readersListAfterDeletion.get(0).getEmail());
    }

    // todo  handling relationships between entities tests!!!!!

}
