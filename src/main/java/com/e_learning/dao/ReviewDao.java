package com.e_learning.dao;

import com.e_learning.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewDao extends BaseDao<Review>{

    Integer countReviewsByCourseId(Long courseId);

    @Query(value = "SELECT ROUND(AVG(r.rating), 2) FROM review r WHERE r.course_id = :courseId", nativeQuery = true)
    Double getAverageRatingByCourseId(@Param("courseId") Long courseId);

    Page<Review> findByCourseId(Long courseId , Pageable pageable );

    List<Review> findByCourseId(Long courseId);
}
