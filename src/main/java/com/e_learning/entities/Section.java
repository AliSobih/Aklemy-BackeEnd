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
@Entity(name = "section")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Section extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "position")
    private int position;

    @Column(name = "title_ar")
    private String titleAr;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private Set<Exam> exams = new HashSet<>();

    // Convenience methods
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setSection(this);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
        exam.setSection(this);
    }
}
