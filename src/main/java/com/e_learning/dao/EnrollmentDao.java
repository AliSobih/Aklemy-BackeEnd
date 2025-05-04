package com.e_learning.dao;

import com.e_learning.entities.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EnrollmentDao extends BaseDao<Enrollment> {
    Integer countEnrollmentsByCourseId(Long courseId);

    Page<Enrollment> findByStudentIdAndApproveTrue(Long userId, Pageable pageable);

    Enrollment findByStudentIdAndCourseId(Long studentId, Long courseId);

}
