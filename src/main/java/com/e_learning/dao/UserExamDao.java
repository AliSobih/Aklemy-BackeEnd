package com.e_learning.dao;

import com.e_learning.entities.UserExam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserExamDao extends BaseDao<UserExam> {

    @Query(value = "select ue.* from user_exam ue join user u on (ue.user_id = u.id) where (ue.status = 0 and u.id = :userId)", nativeQuery = true)
    List<UserExam> findAllByPausedByUserId(@Param("userId") Long userId);

    @Query(value = "select ue.* from user_exam ue join user u on (ue.user_id = u.id) join exam e on (ue.exam_id = e.id) where (ue.status = 0 and e.id= :examId and u.id = :userId)", nativeQuery = true)
    UserExam findPausedUserExam(@Param("examId") Long examId, @Param("userId") Long userId);
}
