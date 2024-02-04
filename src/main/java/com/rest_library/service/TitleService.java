package com.rest_library.service;

import com.rest_library.dto.TitleDto;

import java.util.List;

public interface TitleService {

    List<TitleDto> findAllTitles();

    TitleDto findTitleById(Long id);

    TitleDto saveTitle(TitleDto titleDto);

    TitleDto updateTitle(TitleDto titleDto, Long id);

    void deleteTitle(Long id);

}
