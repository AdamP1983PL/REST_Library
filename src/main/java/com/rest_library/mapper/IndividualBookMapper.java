package com.rest_library.mapper;

import com.rest_library.dto.IndividualBookDto;
import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndividualBookMapper {

    private TitleRepository titleRepository;

    public IndividualBookMapper() {
    }

    @Autowired
    public IndividualBookMapper(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public IndividualBook mapToIndividualBook(IndividualBookDto individualBookDto) {
        return IndividualBook.builder()
                .id(individualBookDto.getId())
                .title(mapIndividualBookTitleToTitle(individualBookDto.getIndividualBookTitle()))
                .status(individualBookDto.getStatus())
                .build();
    }

    public IndividualBookDto mapToIndividualBookDto(IndividualBook individualBook) {
        return IndividualBookDto.builder()
                .id(individualBook.getId())
                .individualBookTitle(individualBook.getTitle().getBookTitle())
                .status(individualBook.getStatus())
                .build();
    }

//    public Title mapIndividualBookTitleToTitle(String individualBookTitle) {
//        Title tempTitleObject = titleRepository.findByBookTitle(individualBookTitle).get(0);
//        if (tempTitleObject != null) {
//            return tempTitleObject;
//        } else {
//            throw new ResourceNotFoundException("Resource not found for title: " + individualBookTitle);
//        }
//    }

    public Title mapIndividualBookTitleToTitle(String individualBookTitle) {
        List<Title> titles = titleRepository.findByBookTitle(individualBookTitle);
        if (!titles.isEmpty()) {
            return titles.get(0);
        } else {
            throw new ResourceNotFoundException("Resource not found for title: " + individualBookTitle);
        }
    }

}
