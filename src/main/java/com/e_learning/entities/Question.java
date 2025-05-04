package com.e_learning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "question")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Question extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    private Set<Exam> exams = new HashSet<>();

    @Column(name = "question_text", columnDefinition = "LONGTEXT", nullable = false)
    private String questionText;

    @Column(name = "question_text_ar", columnDefinition = "LONGTEXT")
    private String questionTextAr;
    //multiple choose ,  choose, DragAndDrop
    @Column(name = "status", nullable = false)
    private String status;

    // easy medium hard
    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<DragAndDrop> dragAndDrops = new HashSet<>();

    // Convenience methods
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void addDragAndDrop(DragAndDrop dragAndDrop) {
        dragAndDrops.add(dragAndDrop);
        dragAndDrop.setQuestion(this);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }
}


