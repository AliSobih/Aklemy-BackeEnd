package com.e_learning.dao;

import com.e_learning.entities.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ExamDao extends BaseDao<Exam> {

//    Page<Exam> findByCourseId(Long courseID , Pageable pageable);

    //    Page<Exam> findBySectionId(Long courseID , Pageable pageable);
    Page<Exam> findByCourseIdAndSectionIdNull(Long courseID, Pageable pageable);

    List<Exam> findByCourseIdAndSectionIdNull(Long courseID);

    List<Exam> findByCourseIdAndSectionId(Long courseID, Long sectionId);

    @Query(value = "select e.* from exam e join course c on (e.course_id = c.id) where c.id = :courseId order by e.section_id asc", nativeQuery = true)
    List<Exam> findAllByCourseId(@Param(value = "courseId") Long courseId);
}
