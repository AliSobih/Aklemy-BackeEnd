package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "description_master")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class DescriptionMaster extends BaseEntity {

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "descriptionMaster", cascade = CascadeType.ALL)
    private Set<DescriptionDetail> details = new HashSet<>();


//    TODO: for arabic
    @Column(name = "note_ar")
    private String noteAr;


    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    public void addDetail(DescriptionDetail descriptionDetail) {
        details.add(descriptionDetail);
    }
}
