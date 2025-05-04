package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO{
    private String name ;
    private String description;
    //    TODO: for arabic
    private String nameAr ;
    private String descriptionAr;
    private String imageUrl ;
}
