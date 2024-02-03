package com.rest_library.service;

import com.rest_library.dto.ReaderDto;

import java.util.List;

public interface ReaderService {

    List<ReaderDto> findAllReaders();

    ReaderDto findReader(Long id);

    ReaderDto saveReader(ReaderDto readerDto);

    ReaderDto updateReader(ReaderDto readerDto, Long id);

    void deleteReader(Long id);

}
