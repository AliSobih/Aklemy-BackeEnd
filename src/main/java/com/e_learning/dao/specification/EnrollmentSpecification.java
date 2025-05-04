package com.e_learning.dao.specification;

import com.e_learning.entities.Course;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

public class EnrollmentSpecification implements Specification<Enrollment> {

    private final String searchValue;
    private final Date enrollmentDateFrom;
    private final Date enrollmentDateTo;
    private final String loggedInUserEmail;
    private final Boolean isAdminUser;

    public EnrollmentSpecification() {
        this.searchValue = null;
        this.enrollmentDateFrom = null;
        this.enrollmentDateTo = null;
        this.loggedInUserEmail = null;
        this.isAdminUser = null;
    }

    public EnrollmentSpecification(String searchValue, Date enrollmentDateFrom, Date enrollmentDateTo, String loggedInUserEmail, Boolean isAdminUser) {
        this.searchValue = searchValue;
        this.enrollmentDateFrom = enrollmentDateFrom;
        this.enrollmentDateTo = enrollmentDateTo;
        this.loggedInUserEmail = loggedInUserEmail;
        this.isAdminUser = isAdminUser;
    }

    @Override
    public Specification<Enrollment> and(Specification<Enrollment> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Enrollment> or(Specification<Enrollment> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Enrollment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Join<Enrollment, Course> enrollmentCourseJoin = root.join("course");
        Join<Course, User> courseUserJoin = enrollmentCourseJoin.join("instructor");
        Predicate searchPredicate = null;

        if (searchValue != null) {
            Predicate titleAr = criteriaBuilder.like(enrollmentCourseJoin.get("titleAr"), "%" + searchValue + "%");
            Predicate title = criteriaBuilder.like(enrollmentCourseJoin.get("title"), "%" + searchValue + "%");
            Predicate courseId = null;
            try {
                courseId = criteriaBuilder.equal(enrollmentCourseJoin.get("id"), Long.parseLong(searchValue));
            } catch (NumberFormatException e) {
                // Ignore the exception if searchValue is not a number
            }

            if (courseId != null) {
                searchPredicate = criteriaBuilder.or(titleAr, title, courseId);
            } else {
                searchPredicate = criteriaBuilder.or(titleAr, title);
            }
        }

        Predicate datePredicate = null;

        if (enrollmentDateFrom != null) {
            datePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("enrollmentDate"), enrollmentDateFrom);
        }
        if (enrollmentDateTo != null) {
            Predicate toDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("enrollmentDate"), enrollmentDateTo);
            if (datePredicate != null) {
                datePredicate = criteriaBuilder.and(datePredicate, toDatePredicate);
            } else {
                datePredicate = toDatePredicate;
            }
        }

        // Combine searchPredicate and datePredicate
        Predicate finalPredicate = criteriaBuilder.conjunction();
        if (searchPredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
        }
        if (datePredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, datePredicate);
        }

        // If the user is not an admin, restrict the query to their courses
        if (isAdminUser != null && !isAdminUser && loggedInUserEmail != null && !loggedInUserEmail.trim().isEmpty()) {
            Predicate loggedInUserIdPredicate = criteriaBuilder.equal(courseUserJoin.get("email"), loggedInUserEmail);
            finalPredicate = criteriaBuilder.and(finalPredicate, loggedInUserIdPredicate);
        }

        return finalPredicate;
    }
}
