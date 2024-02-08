package com.rest_library.service;

import com.rest_library.dto.IndividualBookPostDto;

import java.util.List;

public interface BooksBorrowingStatsService {

    List<IndividualBookPostDto> borrowAvailableBooksByTitle(String title);

//    IndividualBookPostDto returnABook(IndividualBookPostDto individualBookPostDto);

    List<IndividualBookPostDto> returnBooks(List<IndividualBookPostDto> individualBooks);

}
