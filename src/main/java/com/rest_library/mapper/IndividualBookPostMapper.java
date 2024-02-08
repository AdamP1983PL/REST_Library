package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.IndividualBook;
import org.springframework.stereotype.Component;

@Component
public class IndividualBookPostMapper {

    public IndividualBook mapToIndividualBook(IndividualBookPostDto individualPostBookDto) {
        return IndividualBook.builder()
                .id(individualPostBookDto.getId())
                .title(individualPostBookDto.getTitle())
                .status(individualPostBookDto.getStatus())
                .build();
    }

    public IndividualBookPostDto mapToIndividualBookPostDto(IndividualBook individualBook) {
        return IndividualBookPostDto.builder()
                .id(individualBook.getId())
                .title(individualBook.getTitle())
                .status(individualBook.getStatus())
                .build();
    }

}
