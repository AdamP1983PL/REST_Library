package com.rest_library.dto;

import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualBookPostDto {

    private Long id;
    private Title title;
    private Status status;

}
