package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExamDragAndDropDTO extends BaseDTO {

    private String dragItem;

    private String dropItem;

    private String answer;

    private String dragItemAr;

    private String dropItemAr;

    private String answerAr;

    private String randomDropItem;

    private String randomDropItemAr;

    private Boolean mark;

    private Long userExamQuestionId;
}
