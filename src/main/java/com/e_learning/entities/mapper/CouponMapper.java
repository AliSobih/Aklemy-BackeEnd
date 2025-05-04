package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dto.CouponDTO;
import com.e_learning.entities.Coupon;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class CouponMapper implements Mapper<Coupon, CouponDTO> {

    @Autowired
    private CourseDao courseDao ;

    @Override
    public CouponDTO toDTO(Coupon entity) {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(entity.getId());
        couponDTO.setCode(entity.getCode());
        couponDTO.setDiscountPercentage(entity.getDiscountPercentage());
        couponDTO.setValidFrom(entity.getValidFrom());
        couponDTO.setValidTo(entity.getValidTo());
        couponDTO.setIsActive(entity.getIsActive());
        if (entity.getCourse() != null) {
            couponDTO.setCourseId(entity.getCourse().getId());
            couponDTO.setCourseName(entity.getCourse().getTitle());
        }
        return couponDTO;
    }

    @Override
    public Coupon toEntity(CouponDTO dto) {
        Coupon coupon = new Coupon();
        coupon.setId(dto.getId());
        coupon.setCode(dto.getCode());
        coupon.setDiscountPercentage(dto.getDiscountPercentage());
        coupon.setValidFrom(dto.getValidFrom());
        coupon.setValidTo(dto.getValidTo());
        coupon.setIsActive(dto.getIsActive());
        coupon.setCourse(courseDao.getById(dto.getCourseId()));
        // Course should be set externally to avoid circular dependency issues
        return coupon;
    }

    @Override
    public ArrayList<CouponDTO> toDTOs(Collection<Coupon> entities) {
        return entities.stream().map(this::toDTO).collect(toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Coupon> toEntities(Collection<CouponDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(toCollection(ArrayList::new));
    }

    @Override
    public PageResult<CouponDTO> toDataPage(PageResult<Coupon> entities) {
        return new PageResult<>(toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
