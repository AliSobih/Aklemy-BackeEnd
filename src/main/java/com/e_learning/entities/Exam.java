package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "exam")
@NoArgsConstructor
public class Exam extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "description_ar", columnDefinition = "LONGTEXT")
    private String descriptionAr;

    @Column(name = "number_of_questions")
    private int questionsNumber;

    @Column(name = "time")
    private int time;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "exam_question",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions = new HashSet<>();


    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<UserExam> userExams = new HashSet<>();
}
