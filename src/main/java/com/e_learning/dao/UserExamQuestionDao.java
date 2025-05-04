package com.e_learning.dao;

import com.e_learning.entities.UserExamQuestions;

import java.util.Set;

public interface UserExamQuestionDao extends BaseDao<UserExamQuestions> {
    Set<UserExamQuestions> findAllByUserExamId(Long userExamId);
    Set<UserExamQuestions> findByUserExamIdAndTagIsTrue(Long examId);
}
