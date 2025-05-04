package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_exam_questions")
@Setter
@Getter
@NoArgsConstructor
public class UserExamQuestions extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_exam_id", nullable = false)
    private UserExam userExam;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "status")
    private String status;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "tag")
    private Boolean tag = false;

    @OneToMany(mappedBy = "userExamQuestions", cascade = CascadeType.ALL)
    private Set<UserExamAnswers> answers = new HashSet<>();

    @OneToMany(mappedBy = "userExamQuestion", cascade = CascadeType.ALL)
    private Set<UserExamDragAndDrop> dragAndDrops = new HashSet<>();

}
