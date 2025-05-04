package com.e_learning.dao;

import com.e_learning.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionDao extends BaseDao<Question>{
    Page<Question> findAllByCourseId(Long id, Pageable pageable);

    @Query(value = "SELECT q.* FROM exam_question eq JOIN question q ON eq.question_id = q.id WHERE eq.exam_id = :examId " +
            " ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findByExamId(@Param("examId") Long examId, @Param("limit") Integer questionsNumber);
}
