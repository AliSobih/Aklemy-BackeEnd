package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReviewDTO extends BaseDTO {
    private int rating;
    private String comment;
    private Date reviewDate;
    // for insert
    private Long courseId  ;
    private Long userId ;
    // for retrieve
    private String courseNameEn;
    private String courseNameAr;
    private String userName;
    private String userPicture;

//    TODO: for arabic
    private String commentAr;
}
