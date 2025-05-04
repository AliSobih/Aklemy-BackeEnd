package com.e_learning.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "user_exam_answers")
@Getter
@Setter
public class UserExamAnswers extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_exam_question_id")
    private UserExamQuestions userExamQuestions;

    @Column(name = "answer", columnDefinition = "LONGTEXT")
    private String answer;

    @Column(name = "answer_ar", columnDefinition = "LONGTEXT")
    private String answerAr;

    @Column(name = "mark")
    private Boolean mark = false;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

}
