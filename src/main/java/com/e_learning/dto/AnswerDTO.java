package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO extends BaseDTO {

    private String answer ;

    private String answerAr;

    private Boolean isCorrect;

    private Long questionId ;

    private String description ;

    private String descriptionAr ;

}
