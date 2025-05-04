package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponDTO extends BaseDTO {

    private String code ;

    private Double discountPercentage;

    private Date validFrom ;

    private Date validTo;

    private Boolean isActive;

    private Long courseId;

    private String courseName;

}
