package com.rest_library.dto;

import com.rest_library.entity.IndividualBook;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleDto {

    private Long id;
    private String bookTitle;
    private String author;
    private int publicationYear;
    private List<IndividualBook> individualBooks;

}
