package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserDTO extends BaseDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String profilePicture;
    private Boolean enabled;
    private Date dateJoined;
    private Set<CourseDTO> courses;
    private Set<EnrollmentDTO> enrollments;

//    TODO: for arabic
    private String nameAr;
    private String roleAr;
}
