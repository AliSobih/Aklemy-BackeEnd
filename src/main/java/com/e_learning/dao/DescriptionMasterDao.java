package com.e_learning.dao;

import com.e_learning.entities.DescriptionMaster;

import java.util.List;

public interface DescriptionMasterDao extends BaseDao<DescriptionMaster>{
    List<DescriptionMaster> findByCourseId(Long courseId);
}
