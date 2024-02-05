package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.enums.Status;

import java.util.List;

public interface IndividualBookService {

    List<IndividualBookDto> findAllIndividualBooks();

    IndividualBookDto findIndividualBookById(Long id);

    List<IndividualBookDto> findIndividualBooksByBookTitleAndStatus(TitleDto titleDto, Status status);

    Long findNumberOfIndividualBooksByBookTitleAndStatus(TitleDto titleDto, Status status);

    IndividualBookDto createIndividualBook(IndividualBookDto individualBookDto);

    IndividualBookDto updateIndividualBook(IndividualBookDto individualBookDto, Long id);

    IndividualBookDto updateStatus(IndividualBookDto individualBookDto, Long id);

    void deleteIndividualBook(Long id);

}
