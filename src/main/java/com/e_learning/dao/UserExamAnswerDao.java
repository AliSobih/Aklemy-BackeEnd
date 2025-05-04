package com.e_learning.dao;

import com.e_learning.entities.UserExamAnswers;

import java.util.List;

public interface UserExamAnswerDao extends BaseDao<UserExamAnswers> {

    List<UserExamAnswers> findAllByUserExamQuestionsId(Long userExamAnswerDTOS);
}
