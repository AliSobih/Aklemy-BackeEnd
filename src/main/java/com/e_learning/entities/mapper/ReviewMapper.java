package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.ReviewDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.Review;
import com.e_learning.entities.User;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.toCollection;

@Component
public class ReviewMapper implements Mapper<Review, ReviewDTO> {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;

    @Override
    public ReviewDTO toDTO(Review entity) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(entity.getRating());
        reviewDTO.setComment(entity.getComment());
        reviewDTO.setReviewDate(entity.getReviewDate());
        reviewDTO.setCourseId(entity.getCourse().getId());
        reviewDTO.setCourseNameEn(entity.getCourse().getTitle());
        reviewDTO.setCourseNameAr(entity.getCourse().getTitleAr());
        reviewDTO.setUserId(entity.getUser().getId());
        reviewDTO.setUserName(entity.getUser().getName());
        reviewDTO.setUserPicture(entity.getUser().getProfilePicture());
        reviewDTO.setId(entity.getId());
        //        TODO: for arabic
        reviewDTO.setCommentAr(entity.getCommentAr());
        return reviewDTO;
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setReviewDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        review.setId(dto.getId());
        Course course = courseDao.getById(dto.getCourseId());
        course.addReview(review);
        review.setCourse(course);

        User user = userDao.getById(dto.getUserId());
        review.setUser(user);

        //        TODO: for arabic
        review.setCommentAr(dto.getCommentAr());
        return review;
    }

    @Override
    public ArrayList<ReviewDTO> toDTOs(Collection<Review> reviews) {
        return reviews.stream().map(this::toDTO).collect(toCollection(ArrayList<ReviewDTO>::new));
    }

    @Override
    public ArrayList<Review> toEntities(Collection<ReviewDTO> reviewDTOS) {
        return reviewDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Review>::new));
    }

    @Override
    public PageResult<ReviewDTO> toDataPage(PageResult<Review> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
