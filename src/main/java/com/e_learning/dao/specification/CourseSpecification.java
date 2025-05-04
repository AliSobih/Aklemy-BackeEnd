package com.e_learning.dao.specification;

import com.e_learning.entities.Course;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CourseSpecification implements Specification<Course> {

    private final String searchValue;

    public CourseSpecification(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate titlePredicate = null;
        Predicate titleArPredicate = null;

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            titlePredicate = criteriaBuilder.like(root.get("title"), "%" + searchValue + "%");
            titleArPredicate = criteriaBuilder.like(root.get("titleAr"), "%" + searchValue + "%");

            return criteriaBuilder.or(titlePredicate, titleArPredicate);
        }

        return criteriaBuilder.conjunction();
    }
}
