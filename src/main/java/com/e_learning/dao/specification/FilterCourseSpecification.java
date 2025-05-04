package com.e_learning.dao.specification;

import com.e_learning.entities.Course;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class FilterCourseSpecification implements Specification<Course> {

    private final String loggedInUserEmail;
    private final Boolean isAdminUser;

    public FilterCourseSpecification() {
        this.loggedInUserEmail = null;
        this.isAdminUser = null;
    }

    public FilterCourseSpecification(String loggedInUserEmail, Boolean isAdminUser) {
        this.loggedInUserEmail = loggedInUserEmail;
        this.isAdminUser = isAdminUser;
    }

    @Override
    public Specification<Course> and(Specification<Course> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Course> or(Specification<Course> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Join<Course, User> courseUserJoin = root.join("instructor");

        // Combine all predicates
        Predicate finalPredicate = criteriaBuilder.conjunction(); // Start with a conjunction (always true)

        if (isAdminUser != null && !isAdminUser) {
            Predicate loggedInUserIdPredicate = criteriaBuilder.equal(courseUserJoin.get("email"), loggedInUserEmail);
            if (loggedInUserIdPredicate != null) {
                finalPredicate = criteriaBuilder.and(finalPredicate, loggedInUserIdPredicate);
            }
        }

        return finalPredicate;
    }
}
