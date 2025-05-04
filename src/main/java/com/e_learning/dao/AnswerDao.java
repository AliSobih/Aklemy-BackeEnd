package com.e_learning.dao;

import com.e_learning.entities.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerDao extends BaseDao<Answer>{

    @Query(value = "select * from answer a where a.question_id = :questionId and a.is_correct = 1", nativeQuery = true)
    List<Answer> findAllByQuestionId(@Param("questionId") Long questionId);
}
