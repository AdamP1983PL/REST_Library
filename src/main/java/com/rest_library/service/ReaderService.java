package com.rest_library.service;

import com.rest_library.dto.ReaderDto;

import java.util.List;

public interface ReaderService {

    List<ReaderDto> findAllReadersDto();

    ReaderDto findReaderDto(Long id);

    ReaderDto saveReaderDto(ReaderDto readerDto);

    ReaderDto updateReaderDto(ReaderDto readerDto, Long id);

    void deleteReaderDto(Long id);

}
