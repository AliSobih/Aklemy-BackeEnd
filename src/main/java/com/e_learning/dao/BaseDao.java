package com.e_learning.dao;

import com.e_learning.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseDao<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}