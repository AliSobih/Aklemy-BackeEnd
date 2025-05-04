package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "answer")
@NoArgsConstructor
public class Answer extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "answer_text", columnDefinition = "LONGTEXT")
    private String answer;

    @Column(name = "answer_text_ar", columnDefinition = "LONGTEXT")
    private String answerAr;

    @Column(name = "is_correct")
    private Boolean isCorrect ;

    // desc of the correct choose
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description ;

    @Column(name = "description_ar", columnDefinition = "LONGTEXT")
    private String descriptionAr ;

}
