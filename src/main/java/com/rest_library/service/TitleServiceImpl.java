package com.rest_library.service;

import com.rest_library.dto.TitleDto;
import com.rest_library.entity.Title;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.exceptions.TitleAlreadyExistsException;
import com.rest_library.mapper.TitleMapper;
import com.rest_library.repository.TitleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    @Override
    public List<TitleDto> findAllTitles() {
        log.info("====>>>> findAllTitles() execution");
        return titleRepository.findAll().stream()
                .map(titleMapper::mapToTitleDto)
                .collect(Collectors.toList());
    }

    @Override
    public TitleDto findTitleById(Long id) {
        Title tempTitle = titleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title", "id", id));

        log.info("====>>>> findTitle() execution");
        return titleMapper.mapToTitleDto(tempTitle);
    }

    @Override
    public TitleDto saveTitle(TitleDto titleDto) {
        Optional<Title> optionalTitle = titleRepository.findByBookTitle(titleDto.getBookTitle());
        if(optionalTitle.isPresent()){
            throw new TitleAlreadyExistsException("Title already exists in the database");
        }

        log.info("====>>>> saveTitle() execution");
//        Title title = titleMapper.mapToTitle(titleDto);
//        Title savedTitle = titleRepository.save(title);
//        return titleMapper.mapToTitleDto(savedTitle);
        return titleMapper.mapToTitleDto(titleRepository.save(titleMapper.mapToTitle(titleDto)));
    }

    @Override
    public TitleDto updateTitle(TitleDto titleDto, Long id) {
        Title updatedTitle = titleRepository.findById(id)
                .map(title -> {
                    title.setBookTitle(titleDto.getBookTitle());
                    title.setAuthor(titleDto.getAuthor());
//                    title.setPublicationYear(titleDto.getPublicationYear());
                    log.info("====>>>> updateTitle() execution");
                    return titleRepository.save(title);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Title", "id", id));

        return titleMapper.mapToTitleDto(updatedTitle);
    }

    @Override
    public void deleteTitle(Long id) {
        Optional<Title> optionalTitle = titleRepository.findById(id);
        if (optionalTitle.isPresent()) {
            log.info("====>>>> deleteTitle() execution");
            titleRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Title", "id", id);
        }
    }

    @Override
    public TitleDto findByBookTitle(String bookTitle) {
        Title foundTitle = titleRepository.findByBookTitle(bookTitle)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found for title: " + bookTitle));
        log.info("====>>>> findBookByTitle() execution:");

        return titleMapper.mapToTitleDto(foundTitle);
    }
}
