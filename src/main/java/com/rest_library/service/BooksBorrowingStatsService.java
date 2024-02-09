package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;

public interface BooksBorrowingStatsService {

    IndividualBookPostDto borrowAvailableBookByTitle(String title);

    IndividualBookDto returnABook(IndividualBookDto individualBookDto);

}
