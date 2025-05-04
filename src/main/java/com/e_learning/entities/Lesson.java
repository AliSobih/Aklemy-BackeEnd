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
@Entity(name = "lesson")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Lesson extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_url")
    private String contentURL;

    @Column(name = "duration")
    private int duration;

    @Column(name = "position")
    private int position;

    @Column(name = "is_visible")
    private Boolean isVisible;

//    TODO: for arabic
    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "content_type_ar")
    private String contentTypeAr;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
