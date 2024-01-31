package com.rest_library.mapper;

import com.rest_library.dto.TitleDto;
import com.rest_library.entity.Title;
import org.springframework.stereotype.Component;

@Component
public class TitleMapper {

    public Title mapToTitle(TitleDto titleDto) {
        return Title.builder()
                .id(titleDto.getId())
                .bookTitle(titleDto.getBookTitle())
                .author(titleDto.getAuthor())
                .publicationYear(titleDto.getPublicationYear())
                .individualBooks(titleDto.getIndividualBooks())
                .build();
    }

    public TitleDto mapToTitleDto(Title title) {
        return TitleDto.builder()
                .id(title.getId())
                .bookTitle(title.getBookTitle())
                .author(title.getAuthor())
                .publicationYear(title.getPublicationYear())
                .individualBooks(title.getIndividualBooks())
                .build();
    }

}
