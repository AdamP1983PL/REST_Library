package com.rest_library.mapper;

import com.rest_library.dto.BooksBorrowingStatsDto;
import com.rest_library.entity.BooksBorrowingStats;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Reader;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.repository.IndividualBookRepository;
import com.rest_library.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BooksBorrowingStatsMapper {

    @Autowired
    private IndividualBookRepository individualBookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    public BooksBorrowingStats mapToBooksBorrowingStats(BooksBorrowingStatsDto booksBorrowingStatsDto) {
        return BooksBorrowingStats.builder()
                .id(booksBorrowingStatsDto.getId())
                .individualBooks(booksBorrowingStatsDto.getIndividualBooksId().stream()
                        .map(this::mapIndividualBookIdToEntity)
                        .collect(Collectors.toList()))
                .reader(mapReaderIdToEntity(booksBorrowingStatsDto.getReaderId()))
                .borrowingDate(booksBorrowingStatsDto.getBorrowingDate())
                .returnDate(booksBorrowingStatsDto.getReturnDate())
                .build();
    }

    public BooksBorrowingStatsDto mapToBooksBorrowingStatsDto(BooksBorrowingStats booksBorrowingStats) {
        return BooksBorrowingStatsDto.builder()
                .id(booksBorrowingStats.getId())
                .individualBooksId(mapToIds(booksBorrowingStats.getIndividualBooks()))
                .readerId(booksBorrowingStats.getReader().getId())
                .borrowingDate(booksBorrowingStats.getBorrowingDate())
                .returnDate(booksBorrowingStats.getReturnDate())
                .build();
    }

    private List<Long> mapToIds(List<?> entities) {
        return entities.stream()
                .map(entity -> {
                    if (entity instanceof Reader) {
                        return ((Reader) entity).getId();
                    } else if (entity instanceof IndividualBook) {
                        return ((IndividualBook) entity).getId();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public IndividualBook mapIndividualBookIdToEntity(Long id) {
        Optional<IndividualBook> optionalIndividualBook = individualBookRepository.findById(id);
        if (optionalIndividualBook.isPresent()) {
            return optionalIndividualBook.get();
        } else {
            throw new ResourceNotFoundException("Individual book", "id", id);
        }
    }

    public Reader mapReaderIdToEntity(Long id) {
        Optional<Reader> optionalReader = this.readerRepository.findById(id);
        if (optionalReader.isPresent()) {
            return optionalReader.get();
        } else {
            throw new ResourceNotFoundException("Reader", "id", id);
        }
    }

}
