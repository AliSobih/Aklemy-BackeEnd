package com.e_learning.service;

import com.e_learning.dao.ReviewDao;
import com.e_learning.dto.ReviewDTO;
import com.e_learning.entities.Review;
import com.e_learning.entities.mapper.ReviewMapper;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends BaseServiceImp<Review> {
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public JpaRepository<Review, Long> Repository() {
        return reviewDao;
    }

    public PageResult<ReviewDTO> getReviewsByCourseID(Long courseId, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Review> page = reviewDao.findByCourseId(courseId, pageable);
        PageResult<Review> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());
        return reviewMapper.toDataPage(pageResult);
    }
}
