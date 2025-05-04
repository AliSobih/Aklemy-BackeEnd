package com.e_learning.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "user_exam_drag_and_drop")
@Getter
@Setter
public class UserExamDragAndDrop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_exam_question_id")
    private UserExamQuestions userExamQuestion;

    @Column(name = "drag_item", nullable = false, columnDefinition = "LONGTEXT")
    private String dragItem;

    @Column(name = "drop_item", columnDefinition = "LONGTEXT")
    private String dropItem;

    @Column(name = "drag_item_ar", nullable = false, columnDefinition = "LONGTEXT")
    private String dragItemAr;

    @Column(name = "drop_item_ar", columnDefinition = "LONGTEXT")
    private String dropItemAr;

    @Column(name = "answer", columnDefinition = "LONGTEXT")
    private String answer = "";

    @Column(name = "answer_ar", columnDefinition = "LONGTEXT")
    private String answerAr = "";

    @Column(name = "random_drop_item", columnDefinition = "LONGTEXT")
    private String randomDropItem;

    @Column(name = "random_drop_item_ar", columnDefinition = "LONGTEXT")
    private String randomDropItemAr;

    @Column(name = "mark")
    private Boolean mark = false;
}
