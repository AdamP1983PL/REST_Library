package com.rest_library.mapper;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {

    public Reader mapToReader(ReaderDto readerDto) {
        return Reader.builder()
                .id(readerDto.getId())
                .email(readerDto.getEmail())
                .firstName(readerDto.getFirstName())
                .lastName(readerDto.getLastName())
                .startingDate(readerDto.getStartingDate())
                .build();
    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return ReaderDto.builder()
                .id(reader.getId())
                .email(reader.getEmail())
                .firstName(reader.getFirstName())
                .lastName(reader.getLastName())
                .startingDate(reader.getStartingDate())
                .build();
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readersList) {
        return readersList.stream()
                .map(this::mapToReaderDto)
                .collect(Collectors.toList());

    }

}
