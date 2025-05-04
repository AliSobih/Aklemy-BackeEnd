package com.e_learning.dao;

import com.e_learning.entities.Coupon;

import java.util.List;

public interface CouponDao extends BaseDao<Coupon>{
    List<Coupon> findByCourseId(Long id);
}
