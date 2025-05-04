package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CertificateDTO extends BaseDTO{
    private Date issuedDate;
    private String certificateURL;
    private Long enrollmentId;
    private Long courseId;
    private String courseName;
    private String courseNameAr;
    private String instructorName;
    private String instructorNameAr;
    private int totalHours;
    private Long studentId;
    private String studentName;
    private String studentNameAr;
}
