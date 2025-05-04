package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class QuestionDTO extends BaseDTO{
    private Long courseId;

    private String question;

    private String questionAr;

    private String level ;

    private String status ;


    private String subject ;

    private String chapter ;

    private String imagePath ;

    Set<AnswerDTO> answers;

    Set<DragAndDropDTO>  dragAndDrops ;

}
