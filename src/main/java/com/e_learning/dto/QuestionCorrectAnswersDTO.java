package com.e_learning.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionCorrectAnswersDTO {
    List<AnswerDTO> answerDTOS;
    List<DragAndDropDTO> dragAndDropDTOS;
}
