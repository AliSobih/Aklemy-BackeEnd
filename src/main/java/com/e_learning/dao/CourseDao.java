package com.e_learning.dao;

import com.e_learning.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface CourseDao extends BaseDao<Course>{
    Page<Course> findByCategoryId(Long categoryId ,Pageable pageable );

    @Query(value = "select c.* from course c where c.approved = 1", nativeQuery = true)
    Page<Course> findAllApproved(Pageable pageable);
}
