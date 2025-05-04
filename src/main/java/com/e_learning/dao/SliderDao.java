package com.e_learning.dao;

import com.e_learning.entities.Slider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SliderDao extends BaseDao<Slider>{
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM slider WHERE image_url = :url", nativeQuery = true)
    void deleteImagesByName(@Param("url") String url);
}
