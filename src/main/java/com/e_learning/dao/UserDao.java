package com.e_learning.dao;

import com.e_learning.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE user u SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE user u SET u.password = :password WHERE u.id = :id")
    int changePassword(@Param("password") String password, @Param("id") Long id);

}
