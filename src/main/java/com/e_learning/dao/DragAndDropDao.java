package com.e_learning.dao;

import com.e_learning.entities.DragAndDrop;

import java.util.List;

public interface DragAndDropDao extends BaseDao<DragAndDrop> {

    List<DragAndDrop> findAllByQuestionId(Long questionId);
}
