package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "enrollment")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Enrollment extends BaseEntity {

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "approve")
    private boolean approve = false;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

    @OneToOne(mappedBy = "enrollment")
    private Certificate certificate;
}
