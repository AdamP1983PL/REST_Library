package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.enums.Status;

import java.util.List;

public interface IndividualBookService {

    List<IndividualBookDto> findAllIndividualBooks();

    IndividualBookDto findIndividualBookById(Long id);

    List<IndividualBookDto> findIndividualBooksByBookTitleAndStatus(TitleDto titleDto, Status status);

    Long findNumberOfIndividualBooksByBookTitleAndStatus(String bookTitle, Status status);

    IndividualBookDto createIndividualBook(IndividualBookDto individualBookDto);

    IndividualBookPostDto createIndividualBook(IndividualBookPostDto individualBookPostDto);

    IndividualBookDto updateStatus(IndividualBookDto individualBookDto, Long id);

    void deleteIndividualBook(Long id);

}
