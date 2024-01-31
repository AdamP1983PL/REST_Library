package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class IndividualBookMapper {

//    public IndividualBook mapToIndividualBook(IndividualBookDto individualBookDto) {
//        return IndividualBook.builder()
//                .id(individualBookDto.getId())
//                .title(idToTitleEntityMapper.mapIdToTitle())
//                .status(individualBookDto.getStatus())
//                .build();
//    }

    public IndividualBookDto mapToIndividualBookDto(IndividualBook individualBook) {
        return IndividualBookDto.builder()
                .id(individualBook.getId())
                .title_id(individualBook.getTitle().getId())
                .status(individualBook.getStatus())
                .build();
    }

}
