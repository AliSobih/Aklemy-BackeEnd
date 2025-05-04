package com.e_learning.dao;

import com.e_learning.entities.Certificate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateDao extends BaseDao<Certificate> {

    @Query(value = "select ct.* from certificate ct join enrollment e on (ct.enrollment_id = e.id) " +
            "join course c on (e.course_id = c.id) join user u on (e.user_id = u.id) " +
            " where (e.course_id = :courseId and e.user_id = :studentId)", nativeQuery = true)
    Certificate findUserCertificate(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
}
