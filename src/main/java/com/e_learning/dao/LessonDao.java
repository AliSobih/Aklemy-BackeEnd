package com.e_learning.dao;

import com.e_learning.entities.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LessonDao extends BaseDao<Lesson>{
    @Query(value = "SELECT SUM(l.duration) FROM lesson l JOIN section s ON l.section_id = s.id JOIN course c " +
            " ON s.course_id = c.id WHERE c.id = :courseId", nativeQuery = true)
    Integer sumDurationsByCourseId(@Param("courseId") Long courseId);
}
