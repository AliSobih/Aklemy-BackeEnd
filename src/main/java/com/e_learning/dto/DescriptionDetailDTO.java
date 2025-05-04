package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptionDetailDTO extends BaseDTO {
//    TODO: Required
    private String note;

//    todo: for arabic
    private String noteAr;
//    TODO: End
    private Long descriptionId;
}
