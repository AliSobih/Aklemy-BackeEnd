package com.e_learning.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionSearchRequestDTO {

    private String subject ;

    private Long courseId ;

    private String level;

    private String chapter;
}
