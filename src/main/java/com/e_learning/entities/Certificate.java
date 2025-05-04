package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
@Entity(name = "certificate")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Certificate extends BaseEntity {
    @Column(name = "issued_date")
    private Date issuedDate;

    @Column(name = "certificate_url")
    private String certificateURL;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
