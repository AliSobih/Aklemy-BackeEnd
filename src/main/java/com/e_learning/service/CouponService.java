package com.e_learning.service;

import com.e_learning.dao.CouponDao;
import com.e_learning.dto.CouponDTO;
import com.e_learning.entities.Coupon;
import com.e_learning.entities.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService extends BaseServiceImp<Coupon>{
    @Autowired
    private CouponDao couponDao ;
    @Autowired
    private CouponMapper couponMapper ;
    @Override
    public JpaRepository<Coupon, Long> Repository() {
        return couponDao;
    }

    public List<CouponDTO> getCouponsByCourseId(Long courseID) {
        return couponMapper.toDTOs(couponDao.findByCourseId(courseID));
    }
}
