package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "description_detail")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class DescriptionDetail extends BaseEntity{

    @Column(name = "note")
    private String note;

//    TODO: for arabic
    @Column(name = "note_ar")
    private String noteAr;

    @ManyToOne()
    @JoinColumn(name = "description_id")
    private DescriptionMaster descriptionMaster;
}
