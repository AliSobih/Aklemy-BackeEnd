package com.e_learning.controller;

import com.e_learning.dto.ReviewDTO;
import com.e_learning.entities.Review;
import com.e_learning.service.ReviewService;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewController extends BaseController<Review, ReviewDTO> {
    @Autowired
    private ReviewService reviewService ;
    @PostMapping("/course/{courseId}")
    public PageResult<ReviewDTO> getReviewsByCourseId(@PathVariable Long courseId, @RequestBody PageQueryUtil pageUtil) {
        return reviewService.getReviewsByCourseID(courseId, pageUtil);
    }
}
