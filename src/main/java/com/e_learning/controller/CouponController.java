package com.e_learning.controller;

import com.e_learning.dto.CouponDTO;
import com.e_learning.entities.Coupon;
import com.e_learning.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@CrossOrigin
public class CouponController extends BaseController<Coupon,CouponDTO>{
    @Autowired
    private CouponService couponService;
    @GetMapping("/course/{courseID}")
    public List<CouponDTO> getNationalitiesByCourseId(@PathVariable Long courseID){
        return couponService.getCouponsByCourseId(courseID);
    }
}
