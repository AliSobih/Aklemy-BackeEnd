package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ExamDTO extends BaseDTO{
    private String title ;
    private String titleAr ;
    private String description;
    private String descriptionAr;
    private Integer questionsNumber ;
    private Integer time ;
    private Set<QuestionDTO> questions;
    private Long courseId ;
    private Long sectionId ;
    private String status ;
}
