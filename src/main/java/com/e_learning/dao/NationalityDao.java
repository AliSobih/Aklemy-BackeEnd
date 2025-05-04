package com.e_learning.dao;

import com.e_learning.entities.Nationality;

import java.util.List;

public interface NationalityDao extends BaseDao<Nationality> {
    Nationality findByCurrencyAndCourseId(String currency, Long courseId);

    Nationality findByCountryCodeAndCourseId(String countryCode, Long courseId);

    List<Nationality> findBycourseId(Long courseId);
}
