package com.e_learning.dao;

import com.e_learning.entities.UserExamDragAndDrop;

import java.util.List;

public interface UserExamDragAndDropDao extends BaseDao<UserExamDragAndDrop> {

    List<UserExamDragAndDrop> findAllByUserExamQuestionId(Long userExamQuestionId);
}
