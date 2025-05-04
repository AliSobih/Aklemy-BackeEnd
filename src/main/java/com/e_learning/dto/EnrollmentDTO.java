package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentDTO extends BaseDTO {
    private String enrollmentDate;
    private CourseDTO course;
    private UserDTO student;
    private Long studentId;
    private String studentName;
    private String studentNameAr;
    private boolean approve;
    private CertificateDTO certificate;
}
