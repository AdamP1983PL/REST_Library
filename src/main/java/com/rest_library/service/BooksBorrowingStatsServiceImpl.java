package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.enums.Status;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.IndividualBookMapper;
import com.rest_library.mapper.IndividualBookPostMapper;
import com.rest_library.repository.IndividualBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BooksBorrowingStatsServiceImpl implements BooksBorrowingStatsService {

    private final IndividualBookRepository individualBookRepository;
    private final IndividualBookPostMapper individualBookPostMapper;
    private final IndividualBookMapper individualBookMapper;

    @Override
    public IndividualBookPostDto borrowAvailableBookByTitle(String title) {
        IndividualBook libraryBooksByTitle = individualBookRepository.findFirstByTitleBookTitleAndStatus(title, Status.AVAILABLE)
                .orElseThrow(() -> new ResourceNotFoundException("No book with title: " + title + " available."));


        libraryBooksByTitle.setStatus(Status.IN_CIRCULATION);
        individualBookRepository.save(libraryBooksByTitle);

        log.info("====>>>> borrowAvailableBookByTitle(String title) execution");
        return individualBookPostMapper.mapToIndividualBookPostDto(libraryBooksByTitle);
    }

    @Override
    public IndividualBookDto returnABook(IndividualBookDto individualBookDto) {
        List<IndividualBook> allLibraryBooks = individualBookRepository.findAll();
        log.info("====>>>> allLibraryBooks: " + allLibraryBooks);
        IndividualBook returnedBook = individualBookMapper.mapToIndividualBook(individualBookDto);
        log.info("====>>>> returnedBook: " + returnedBook);

//        if (!allLibraryBooks.contains(returnedBook)) {
//            throw new IllegalArgumentException("Returned book doesn't belong to the library's collection");
//        } else {
        returnedBook.setStatus(Status.AVAILABLE);
        individualBookRepository.save(returnedBook);
//        }

        log.info("====>>>> returnABook(IndividualBookPostDto individualBookPostDto) execution:");
        return individualBookMapper.mapToIndividualBookDto(returnedBook);
    }

}
