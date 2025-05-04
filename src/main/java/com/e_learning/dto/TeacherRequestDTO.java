package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherRequestDTO extends BaseDTO{

    private String name;

    private String email;

    private String phone;

    private String city ;

    private String jobTitle;

    private String coursesName;

    private Integer trainerNumber ;

    private String notes ;

}
