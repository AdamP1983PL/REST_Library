package com.rest_library.controller;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.enums.Status;
import com.rest_library.service.IndividualBookServiceImpl;
import com.rest_library.service.TitleServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/individual-books")
public class IndividualBookController {

    private final IndividualBookServiceImpl individualBookServiceImpl;
    private final TitleServiceImpl titleService;

    @GetMapping("/")
    public ResponseEntity<List<IndividualBookDto>> findAllIndividualBooks() {
        log.info("====>>>> IndividualBookController -> findAllIndividualBooks() execution:");
        List<IndividualBookDto> individualBookDtoList = individualBookServiceImpl.findAllIndividualBooks();
        return new ResponseEntity<>(individualBookDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndividualBookDto> findIndividualBookById(@PathVariable("id") Long id) {
        log.info("====>>>> IndividualBookController -> findIndividualBookById(): " + id + " execution:");
        IndividualBookDto individualBookDto = individualBookServiceImpl.findIndividualBookById(id);
        return new ResponseEntity<>(individualBookDto, HttpStatus.OK);
    }

    @GetMapping("/title-status")
    public ResponseEntity<List<IndividualBookDto>> findIndividualBookByTitleAndStatus
            (@RequestParam("title") String bookTitle,
             @RequestParam("status") Status status) {
        log.info("====>>>> IndividualBookController -> findIndividualBookByTitleAndStatus() execution:");

        TitleDto titleDto = titleService.findByBookTitle(bookTitle);

        List<IndividualBookDto> individualBooksDto = individualBookServiceImpl
                .findIndividualBooksByBookTitleAndStatus(titleDto, status);

        return new ResponseEntity<>(individualBooksDto, HttpStatus.OK);
    }

    @GetMapping("/number-of-books")
    public Long showNumberOfAvailableBooksByTitle(@RequestParam TitleDto titleDto,
                                                  @RequestParam Status status) {
        log.info("====>>>> IndividualBookController -> showNumberOfAvailableBooksByTitle() execution:");
        return individualBookServiceImpl.findNumberOfIndividualBooksByBookTitleAndStatus(titleDto, status);
    }

    @PostMapping("/")
    public ResponseEntity<IndividualBookDto> createIndividualBook(@RequestBody IndividualBookDto individualBookDto) {
        log.info("====>>>> IndividualBookController -> createIndividualBook() execution:");
        IndividualBookDto savedIndividualBookDto = individualBookServiceImpl.createIndividualBook(individualBookDto);
        return new ResponseEntity<>(savedIndividualBookDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndividualBookDto> updateIndividualBookDto(@RequestBody IndividualBookDto individualBookDto,
                                                                     @PathVariable("id") Long id) {
        log.info("====>>>> IndividualBookController -> updateIndividualBookDto() execution:");
        IndividualBookDto updatedIndividualBookDto = individualBookServiceImpl.updateIndividualBook(individualBookDto, id);
        return new ResponseEntity<>(updatedIndividualBookDto, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<IndividualBookDto> updateIndividualBookStatus(@RequestBody IndividualBookDto individualBookDto,
                                                                        @PathVariable("id") Long id) {

        log.info("====>>>> IndividualBookController " +
                "-> updateIndividualBookStatus() for id: " + id +
                ", individual book: " + individualBookDto.getTitle().getBookTitle() + " execution:");
        IndividualBookDto updatedIndividualBookDto = individualBookServiceImpl.updateStatus(individualBookDto, id);
        return new ResponseEntity<>(updatedIndividualBookDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndividualBook(@PathVariable("id") Long id) {
        log.info("====>>>> IndividualBookController -> deleteIndividualBook() execution:");
        individualBookServiceImpl.deleteIndividualBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}















