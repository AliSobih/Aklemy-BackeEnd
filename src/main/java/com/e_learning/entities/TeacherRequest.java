package com.e_learning.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
@Getter
@Setter
@Entity(name = "teacher_request")
public class TeacherRequest extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "courses_name")
    private String coursesName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "trainer_number")
    private Integer trainerNumber;

    @Column(name = "notes")
    private String notes;


}
