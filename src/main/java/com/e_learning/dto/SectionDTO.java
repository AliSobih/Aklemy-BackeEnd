package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SectionDTO extends BaseDTO{
//    TODO: Required fields
    private String title;
    private int position;
//    TODO: end

//    TODO: Required if add section only without add course
    private Long courseID ;
//    TODO: end

//    todo: for arabic
    private String titleAr;

    private Set<LessonDTO> lessons;
}
