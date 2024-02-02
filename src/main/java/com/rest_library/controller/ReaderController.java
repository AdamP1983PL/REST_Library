package com.rest_library.controller;

import com.rest_library.dto.ReaderDto;
import com.rest_library.service.ReaderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/readers")
public class ReaderController {

    private final ReaderServiceImpl readerService;

    @GetMapping("/")
    public ResponseEntity<List<ReaderDto>> findAllReadersDto() {
        log.info("====>>>> ReaderController -> findAllReadersDto() execution start:");
        List<ReaderDto> readersDto = readerService.findAllReadersDto();
        return new ResponseEntity<>(readersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDto> findReaderDtoById(@PathVariable("id") Long id) {
        log.info("====>>>> ReaderController ->  findReaderDtoById for id: " + id + " execution started: ");
        ReaderDto readerDto = readerService.findReaderDto(id);
        return new ResponseEntity<>(readerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReaderDto> addReaderDto(@RequestBody ReaderDto readerDto) {
        log.info("====>>>> ReaderController ->  addReaderDto() execution started: ");
        ReaderDto savedReaderDto = readerService.saveReaderDto(readerDto);
        return new ResponseEntity<>(savedReaderDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReaderDto> updateReaderDto(@RequestBody ReaderDto readerDto,
                                                     @PathVariable("id") Long id) {
        log.info("====>>>> ReaderController -> updateReaderDto() for id: " + id + " execution started: ");
        ReaderDto updatedReaderDto = readerService.updateReaderDto(readerDto, id);
        return new ResponseEntity<>(updatedReaderDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReaderDto(@PathVariable("id") Long id) {
        log.info("====>>>> ReaderController -> deleteReaderDto() for id: " + id + " execution started: ");
        readerService.deleteReaderDto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // todo AOP for all methods


}
