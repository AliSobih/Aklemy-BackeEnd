package com.e_learning.dao;

import com.e_learning.entities.TeacherRequest;

import java.util.Optional;

public interface TeacherRequestDao extends BaseDao<TeacherRequest> {

    Optional<TeacherRequest> findByEmail(String email);
}
