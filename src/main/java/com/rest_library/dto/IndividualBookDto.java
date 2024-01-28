package com.rest_library.dto;

import com.rest_library.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualBookDto {

    private long id;
    private Long title_id;
    private Status status;

}
