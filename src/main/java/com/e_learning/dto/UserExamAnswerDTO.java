package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExamAnswerDTO extends BaseDTO{

    private String answer ;

    private String answerAr;

    private Boolean mark ;

    private Boolean isCorrect ;

    private Long userExamQuestionId ;

}
