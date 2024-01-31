package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.entity.IndividualBook;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IndividualBookMapper {

    public IndividualBook mapToIndividualBook(IndividualBookDto individualBookDto) {
        return IndividualBook.builder()
                .id(individualBookDto.getId())
                .title(individualBookDto.getTitle())
                .status(individualBookDto.getStatus())
                .build();
    }

    public IndividualBookDto mapToIndividualBookDto(IndividualBook individualBook) {
        return IndividualBookDto.builder()
                .id(individualBook.getId())
                .title(individualBook.getTitle())
                .status(individualBook.getStatus())
                .build();
    }

}
