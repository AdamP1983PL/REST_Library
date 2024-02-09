package com.rest_library.controller;

import com.rest_library.dto.TitleDto;
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
@RequestMapping("api/v1/titles")
public class TitleController {

    private final TitleServiceImpl titleService;

    @GetMapping("/")
    public ResponseEntity<List<TitleDto>> findAllTitles() {
        log.info("====>>>> TitleController -> findAllTitles() execution:");
        List<TitleDto> titlesDto = titleService.findAllTitles();
        return new ResponseEntity<>(titlesDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitleDto> findTitleById(@PathVariable("id") Long id) {
        log.info("====>>>> TitleController -> findTitleById() execution:");
        TitleDto titleDto = titleService.findTitleById(id);
        return new ResponseEntity<>(titleDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TitleDto> createTitle(@RequestBody TitleDto titleDto) {
        log.info("====>>>> TitleController -> createTitle() execution:");
        TitleDto savedTitleDto = titleService.saveTitle(titleDto);
        return new ResponseEntity<>(savedTitleDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitleDto> updateTitle(@RequestBody TitleDto titleDto,
                                                @PathVariable("id") Long id) {
        log.info("====>>>> TitleController -> updateTitle() for id: " + id + " execution: ");
        TitleDto updatedTitleDto = titleService.updateTitle(titleDto, id);
        return new ResponseEntity<>(updatedTitleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable("id") Long id) {
        log.info("====>>>> TitleController -> deleteTitle() for id: " + id + " execution: ");
        titleService.deleteTitle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
