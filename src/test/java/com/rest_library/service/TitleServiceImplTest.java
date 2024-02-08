package com.rest_library.service;

import com.rest_library.dto.TitleDto;
import com.rest_library.entity.Title;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.exceptions.TitleAlreadyExistsException;
import com.rest_library.mapper.TitleMapper;
import com.rest_library.repository.TitleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TitleServiceImplTest {

    private Title testTitle;
    private Title testTitle2;
    private TitleDto testTitleDto;
    private TitleDto testTitleDto2;

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
    }

    @AfterEach
    void cleanUp() {
        titleRepository.deleteAll();
    }

    @Mock
    private TitleRepository titleRepository;

    @Mock
    private TitleMapper titleMapper;

    @InjectMocks
    private TitleServiceImpl titleServiceImpl;

//    @Test
//    @DisplayName("Testing saveTitle(TitleDto titleDto) method.")
//    public void givenTitleDtoObject_whenSaveTitle_thenReturnSavedTitleObject() {
//        // given
//        given(titleRepository.findByBookTitle(testTitle.getBookTitle())).willReturn((List<Title>) testTitle);
//        given(titleMapper.mapToTitle(testTitleDto)).willReturn(testTitle);
//        given(titleMapper.mapToTitleDto(any())).willReturn(testTitleDto2);
//        given(titleServiceImpl.saveTitle(testTitleDto)).willReturn(testTitleDto);
//
//        /*stubbing for mapToReaderDto using any() as the argument matcher,
//        this ensures that the mapToReaderDto method is considered valid regardless of the argument
//        passed to it during the test.*/
//
//        // when
//        TitleDto savedTitleDto = titleServiceImpl.saveTitle(testTitleDto);
//
//        // then
//        assertAll(
//                () -> assertNotNull(savedTitleDto),
//                () -> assertEquals("test title 1", savedTitleDto.getBookTitle()),
//                () -> assertEquals("test author 1", savedTitleDto.getAuthor())
//        );
//    }

//    @Test
//    @DisplayName("Testing saveTitle(TitleDto titleDto) method that throws TitleAlreadyExistException.")
//    public void givenExistingBookTitle_whenSaveTitle_thenThrowsTitleAlreadyExistsException() {
//        // given
//        given(titleRepository.findByBookTitle(testTitle.getBookTitle())).willReturn(Optional.ofNullable(testTitle));
//
//        // when, then
//        assertThrows(TitleAlreadyExistsException.class, () -> {
//            titleServiceImpl.saveTitle(testTitleDto);
//        });
//        verify(titleRepository, never()).save(any(Title.class));
//    }

    @Test
    @DisplayName("Testing findAllTitles() method - positive scenario (valid input)")
    public void givenTitlesList_whenFindAllTitles_thenReturnAllTitles() {
        // given
        given(titleMapper.mapToTitleDto(testTitle)).willReturn(testTitleDto);
        given(titleMapper.mapToTitleDto(testTitle2)).willReturn(testTitleDto2);
        given(titleRepository.findAll()).willReturn(List.of(testTitle, testTitle2));

        // when
        List<TitleDto> titleDtoList = titleServiceImpl.findAllTitles();

        // then
        assertAll(
                () -> assertNotNull(titleDtoList),
                () -> assertEquals(2, titleDtoList.size()),
                () -> assertEquals("test title 1", titleDtoList.get(0).getBookTitle()),
                () -> assertEquals("test title 2", titleDtoList.get(1).getBookTitle()),
                () -> assertEquals("test author 1", titleDtoList.get(0).getAuthor()),
                () -> assertEquals("test author 2", titleDtoList.get(1).getAuthor())
        );
    }

    @Test
    @DisplayName("Testing findAllTitles() method - negative scenario (empty List).")
    public void givenEmptyTitlesList_whenFindAllTitles_thenReturnEmptyTitleList() {
        // given
        given(titleRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<TitleDto> emptyList = titleServiceImpl.findAllTitles();

        // then
        assertAll(
                () -> assertTrue(emptyList.isEmpty()),
                () -> assertEquals(0, emptyList.size())
        );
    }

    @Test
    @DisplayName("Testing findTitleById(Long id) method - positive scenario (valid input).")
    public void givenTitleObject_whenFindTitleById_thenReturnTitleDtoObject() {
        // given
        given(titleRepository.findById(testTitle.getId())).willReturn(Optional.ofNullable(testTitle));
        given(titleMapper.mapToTitleDto(testTitle)).willReturn(testTitleDto);

        // when
        TitleDto actualTitle = titleServiceImpl.findTitleById(testTitleDto.getId());

        // then
        assertAll(
                () -> assertNotNull(actualTitle),
                () -> assertEquals("test title 1", actualTitle.getBookTitle()),
                () -> assertEquals("test author 1", actualTitle.getAuthor())
        );
    }

    @Test
    @DisplayName("Testing findTitleById(Long id) method that throws ResourceNotFoundException.")
    public void givenEmptyTitleList_whenFindTitleById_thenThrowResourceNotFoundException() {
        // given
        given(titleRepository.findById(testTitle.getId())).willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            titleServiceImpl.findTitleById(testTitleDto.getId());
        });
        verify(titleRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Testing updateTitle(TitleDto titleDto, Long id) method.")
    public void givenTitleObject_whenUpdateTitle_thenReturnUpdatedTitleObject() {
        // given
        Title updatedTitle = Title.builder()
                .id(2L)
                .bookTitle("updated title")
                .author("updated author")
                .publicationYear(2222)
                .individualBooks(Collections.emptyList())
                .build();

        TitleDto updatedTitleDto = TitleDto.builder()
                .id(2L)
                .bookTitle("updated title")
                .author("updated author")
                .build();

        given(titleRepository.findById(testTitle.getId())).willReturn(Optional.ofNullable(testTitle));
        given(titleRepository.save(testTitle)).willReturn(updatedTitle);
        given(titleMapper.mapToTitleDto(any())).willReturn(updatedTitleDto);

        // when
        TitleDto actualTitleDto = titleServiceImpl.updateTitle(testTitleDto, testTitleDto.getId());

        // then
        assertAll(
                () -> assertNotNull(actualTitleDto),
                () -> assertEquals("updated title", actualTitleDto.getBookTitle()),
                () -> assertEquals("updated author", actualTitleDto.getAuthor())
        );
    }

    @Test
    @DisplayName("Testing deleteTitle() method - positive scenario (valid input).")
    public void givenTitleObjectList_whenDeleteTitle_thenReturnEmptyList() {
        // given
        List<Title> titleList = List.of(testTitle, testTitle2);
        given(titleRepository.findById(testTitle.getId())).willReturn(Optional.ofNullable(testTitle));
        given(titleRepository.findById(testTitle2.getId())).willReturn(Optional.ofNullable(testTitle2));

        // when
        titleServiceImpl.deleteTitle(testTitle.getId());
        titleServiceImpl.deleteTitle(testTitle2.getId());

        // then
        verify(titleRepository, times(1)).deleteById(testTitle.getId());
        verify(titleRepository, times(1)).deleteById(testTitle2.getId());
    }

    @Test
    @DisplayName("Testing deleteTitle() method that throws ResourceNotFoundException.")
    public void givenEmptyTitleList_whenDeleteTitle_thenThrowsResourceNotFoundException() {
        // given
        given(titleRepository.findById(testTitle.getId())).willReturn(Optional.empty());

        // when, then
        assertThrows(ResourceNotFoundException.class, () -> {
            titleServiceImpl.deleteTitle(testTitle.getId());
        });
        verify(titleRepository, times(1)).findById(testTitle.getId());
        verify(titleRepository, never()).deleteById(testTitle.getId());
    }

    @Test
    @DisplayName("Testing findByBookTitle() method.")
    public void given_when_then() {
        // given todo write test


        // when


        // then


    }

}

