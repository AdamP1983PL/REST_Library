package com.rest_library.dto;

import com.rest_library.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualBookDto {

    private Long id;
    private Long title_id;
    private Status status;

}
