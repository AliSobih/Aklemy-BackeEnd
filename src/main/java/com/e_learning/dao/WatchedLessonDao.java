package com.e_learning.dao;

import com.e_learning.entities.WatchedLesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface WatchedLessonDao extends BaseDao<WatchedLesson> {

    Set<WatchedLesson> findByUserIdAndSectionId(Long userId, Long sectionId);

    @Query("SELECT wl.lesson.id FROM WatchedLesson wl WHERE wl.user.id = :userId AND wl.course.id IN :coursesIds")
    Set<Long> findWatchedLessonIdsByUserAndCourses(@Param("userId") Long userId, @Param("coursesIds") List<Long> coursesIds);

    int countByUserIdAndSectionId(Long userId, Long sectionId);

    boolean existsByUserIdAndCourseIdAndSectionIdAndLessonId(Long userId, Long courseId, Long sectionId, Long lessonId);
}
