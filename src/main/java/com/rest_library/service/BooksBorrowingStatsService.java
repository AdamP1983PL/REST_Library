package com.rest_library.service;

import com.rest_library.dto.IndividualBookPostDto;

import java.util.List;

public interface BooksBorrowingStatsService {

    IndividualBookPostDto borrowAvailableBookByTitle(String title);

//    IndividualBookPostDto returnABook(IndividualBookPostDto individualBookPostDto);

    List<IndividualBookPostDto> returnBooks(List<IndividualBookPostDto> individualBooks);

}
