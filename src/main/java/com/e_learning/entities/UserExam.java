package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_exam")
@Getter
@Setter
@NoArgsConstructor
public class UserExam extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<UserExamQuestions> examQuestions = new HashSet<>();

    @Column(name = "correct_answers")
    private Integer correctAnswers = 0;

    @Column(name = "wrong_answers")
    private Integer wrongAnswers = 0;

    @Column(name = "question_number")
    private Integer questionNumber;

    @Column(name = "remaining_time")
    private Integer remainingTime;

    @Column(name = "net_time")
    private Integer netTime;

    // Exam will be arabic or english ar or en
    @Column(name = "language", nullable = false)
    private String language;

    // 0 for open 1 for close
    @Column(name = "status")
    private Boolean status = false;

}
