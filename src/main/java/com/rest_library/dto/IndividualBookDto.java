package com.rest_library.dto;

import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualBookDto {

//    private Long id;
//    private Title title;
//    private Status status;

    private Long id;
    private Title title;
    private Status status;

}
