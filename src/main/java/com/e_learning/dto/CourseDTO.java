package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CourseDTO extends BaseDTO{
//    Required fields
    private String title;
    private String description;
    private String language;
    private Double price;
    private Double fixedPrice;
    private Long categoryId;
    private Long userId;
    private String instructorNameEn;
    private String instructorNameAr;

//     for arabic
    private String titleAr;
    private String descriptionAr;
//     End
    private String imageURL ;
    private Date updatedDate;
    private Set<SectionDTO> sections;
    private Set<DescriptionMasterDTO> descriptionMasterDTOS;

    private int enrollmentsNum;
    private int reviewsNum;
    private Double averageRating;
    // for displaying the amount and the currency based on the loggedIn user nationality
    private String formattedAmountAr;
    private String formattedAmountEn;

    private String formattedAmountArBeforeDiscount;
    private String formattedAmountEnBeforeDiscount;

    private String totalHours;
    private Long pdfCount ;

    private Boolean approved;

    private List<Integer> ratingCounts;
}
