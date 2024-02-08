package com.rest_library.service;

import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.enums.Status;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.IndividualBookPostMapper;
import com.rest_library.repository.IndividualBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BooksBorrowingStatsServiceImpl implements BooksBorrowingStatsService {

    private final IndividualBookRepository individualBookRepository;
    private final IndividualBookPostMapper individualBookPostMapper;

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
    public List<IndividualBookPostDto> returnBooks(List<IndividualBookPostDto> individualBooks) {
        List<IndividualBook> allLibraryBooks = individualBookRepository.findAll();
        List<IndividualBook> returnedBooks = individualBooks.stream()
                .map(individualBookPostMapper::mapToIndividualBook)
                .collect(Collectors.toList());

        boolean allBooksBelongToLibrary = returnedBooks.stream()
                .allMatch(libraryBook -> allLibraryBooks.stream()
                        .anyMatch(returnedBook -> returnedBook.getId().equals(libraryBook.getId())));

        if (allBooksBelongToLibrary) {
            returnedBooks.forEach(book -> book.setStatus(Status.AVAILABLE));
            individualBookRepository.saveAll(returnedBooks);

            log.info("====>>>> returnBooks(List<IndividualBookPostDto> individualBooks) execution");
            return returnedBooks.stream()
                    .map(individualBookPostMapper::mapToIndividualBookPostDto)
                    .collect(Collectors.toList());

        } else {
            throw new IllegalArgumentException("Not all returned books belong to the library's collection");
        }
    }
}


//    @Override
//    public List<IndividualBookPostDto> borrowAvailableBooksByTitle(String title) {
//        Optional<List<IndividualBook>> optionalBooks = Optional.of(individualBookRepository.findAll());
//
//        List<IndividualBookPostDto> availableBooks = optionalBooks.stream()
//                .map(book -> individualBookPostMapper.mapToIndividualBookPostDto((IndividualBook) book))
//                .filter(status -> ((IndividualBookPostDto) status).getStatus().equals(Status.AVAILABLE))
//                .collect(Collectors.toList());
//
//        if (availableBooks.isEmpty()) {
//            throw new ResourceNotFoundException("No Books with title: " + title + " available.");
//        }
//        return availableBooks;
//    }


//    @Override
//    public IndividualBookPostDto returnABook(IndividualBookPostDto individualBookPostDto) {
//        if (individualBookPostDto == null || individualBookPostDto.getId() == null) {
//            throw new IllegalArgumentException("Invalid book.");
//        }
//
//        IndividualBook returnedIndividualBook = individualBookRepository.findById(individualBookPostDto.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + individualBookPostDto.getId()));
//
//        returnedIndividualBook.setStatus(Status.AVAILABLE);
//        returnedIndividualBook = individualBookRepository.save(returnedIndividualBook);
//
//        log.info("====>>>> returnABook(String title) execution");
//        return individualBookPostMapper.mapToIndividualBookPostDto(returnedIndividualBook);
//    }
