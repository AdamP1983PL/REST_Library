package com.rest_library.service;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.enums.Status;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.IndividualBookMapper;
import com.rest_library.mapper.IndividualBookPostMapper;
import com.rest_library.repository.IndividualBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class IndividualBookServiceImpl implements IndividualBookService {

    private final IndividualBookRepository individualBookRepository;
    private final IndividualBookMapper individualBookMapper;
    private final IndividualBookPostMapper individualBookPostMapper;

    @Override
    public List<IndividualBookDto> findAllIndividualBooks() {
        log.info("====>>>> findAllIndividualBooks() execution");
        return individualBookRepository.findAll().stream()
                .map(individualBookMapper::mapToIndividualBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public IndividualBookDto findIndividualBookById(Long id) {
        IndividualBook tempIndividualBook = individualBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IndividualBook", "id", id));

        log.info("====>>>> findIndividualBook() execution");
        return individualBookMapper.mapToIndividualBookDto(tempIndividualBook);
    }

    @Override
    public List<IndividualBookDto> findIndividualBooksByBookTitleAndStatus(TitleDto titleDto, Status status) {
        log.info("====>>>> findIndividualBooksByBookTitleAndStatus() execution");
        return individualBookRepository
                .findByTitleBookTitleAndStatus(titleDto.getBookTitle(), Status.AVAILABLE).stream()
                .map(individualBookMapper::mapToIndividualBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long findNumberOfIndividualBooksByBookTitleAndStatus(String bookTitle, Status status) {
        log.info("====>>>> findNumberOfIndividualBooksByBookTitleAndStatus() execution");
        return (long) individualBookRepository.findByTitleBookTitleAndStatus(bookTitle, Status.AVAILABLE)
                .size();
    }

    @Override
    public IndividualBookDto createIndividualBook(IndividualBookDto individualBookDto) {
        IndividualBook toBeSavedIndividualBook = individualBookMapper.mapToIndividualBook(individualBookDto);
        IndividualBook savedIndividualBook = individualBookRepository.save(toBeSavedIndividualBook);
        log.info("====>>>> createIndividualBook() execution");
        return individualBookMapper.mapToIndividualBookDto(savedIndividualBook);
    }

    @Override
    public IndividualBookPostDto createIndividualBook(IndividualBookPostDto individualBookPostDto) {
        IndividualBook toBeSavedIndividualBook = individualBookPostMapper.mapToIndividualBook(individualBookPostDto);
        IndividualBook savedIndividualBook = individualBookRepository.save(toBeSavedIndividualBook);
        log.info("====>>>> createIndividualBook() execution");
        return individualBookPostMapper.mapToIndividualBookPostDto(savedIndividualBook);
    }

    @Override
    public IndividualBookDto updateStatus(IndividualBookDto individualBookDto, Long id) {
        Status status;
        try {
            status = Status.valueOf(individualBookDto.getStatus().toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid status: " + individualBookDto.getStatus() +
                    ". Accepted values are: " + Arrays.toString(Status.values()));
        }

        IndividualBook individualBookUpdatedStatus = individualBookRepository.findById(id)
                .map(individualBook -> {
                    individualBook.setStatus(status);
                    log.info("====>>>> updateStatus() execution");
                    return individualBookRepository.save(individualBook);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Individual Book", "id", id));
        return individualBookMapper.mapToIndividualBookDto(individualBookUpdatedStatus);
    }

    @Override
    public void deleteIndividualBook(Long id) {
        Optional<IndividualBook> optionalIndividualBook = individualBookRepository.findById(id);
        if (optionalIndividualBook.isPresent()) {
            log.info("====>>>> deleteIndividualBook() execution");
            individualBookRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Individual Book", "id", id);
        }
    }
}
