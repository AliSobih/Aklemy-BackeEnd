package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NationalityDTO extends BaseDTO{
    private String name;
    private String currency;
    private Double factor;
    private Double rateExchange ;
//    TODO: for arabic
    private String nameAr;
    private String currencyAr;
    private Long courseId;

    private String countryCode;
}
