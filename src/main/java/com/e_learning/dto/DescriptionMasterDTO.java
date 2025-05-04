package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DescriptionMasterDTO extends BaseDTO{
//    TODO: Required
    private String note;

//    todo: for arabic
    private String noteAr;

//    TODO: end
    private Set<DescriptionDetailDTO> details;
    private Long courseId;
}
