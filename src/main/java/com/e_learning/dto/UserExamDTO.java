package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserExamDTO extends BaseDTO {

    private Long examId;

    private Long userId;

    private String title ;

    private String titleAr;

    private String description ;

    private String descriptionAr;

    private Integer wrongAnswers;

    private Integer correctAnswers;

    private Integer remainingTime;

    private Integer netTime;

    // Exam will be arabic or english ar or en
    private String language;

    private Boolean status;

    private Set<UserExamQuestionDTO> userExamQuestions;

}
