package com.rest_library.controller;

import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.service.BooksBorrowingStatsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/stats")
public class BooksBorrowingStatsController {

    private BooksBorrowingStatsServiceImpl booksBorrowingStatsServiceImpl;

    @PostMapping("/borrow/{title}")
    public ResponseEntity<IndividualBookPostDto> borrowAvailableBook(@PathVariable("title") String title) {
        IndividualBookPostDto borrowedBook = booksBorrowingStatsServiceImpl.borrowAvailableBookByTitle(title);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

//    @PostMapping("/return/")
//    public ResponseEntity<IndividualBookPostDto> returnBooks(@RequestBody IndividualBookPostDto individualBookPostDto) {
//        IndividualBookPostDto returnedBook = booksBorrowingStatsServiceImpl.returnBooks()
//    }

}
