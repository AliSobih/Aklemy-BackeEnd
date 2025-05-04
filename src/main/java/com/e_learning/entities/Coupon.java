package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@Entity(name = "coupon")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Coupon extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "valid_from")
    private Date validFrom;

    @Column(name = "valid_to")
    private Date validTo;

    @Column(name = "is_active")
    private Boolean isActive;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Convenience method
    public void assignToCourse(Course course) {
        this.course = course;
    }
}
