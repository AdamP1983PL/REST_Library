package com.rest_library.mapper;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {

    public Reader mapToReader(ReaderDto readerDto) {
        return Reader.builder()
                .id(readerDto.getId())
                .firstName(readerDto.getFirstName())
                .lastName(readerDto.getLastName())
                .startingDate(readerDto.getStartingDate())
                .build();
    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return ReaderDto.builder()
                .id(reader.getId())
                .firstName(reader.getFirstName())
                .lastName(reader.getLastName())
                .startingDate(reader.getStartingDate())
                .build();
    }

}
