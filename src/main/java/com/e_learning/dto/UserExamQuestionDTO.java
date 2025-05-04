package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserExamQuestionDTO extends BaseDTO {

    private String questionText;

    private String questionTextAr;

    private String status;

    private Boolean isCorrect;

    private Boolean tag;

    private Long questionId;

    private Long userExamId;

    private String imagePath;

    private Set<UserExamAnswerDTO> userExamAnswers;

    private Set<UserExamDragAndDropDTO> userExamDragAndDrops = new HashSet<>();

}
