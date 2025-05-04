package com.e_learning.dao.specification;

import com.e_learning.entities.Question;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QuestionSpecification implements Specification<Question> {

    private final String subject;
    private final Long courseId;
    private final String level;
    private final String chapter;

    public QuestionSpecification(String subject, Long courseId, String level, String chapter) {
        this.subject = subject;
        this.courseId = courseId;
        this.level = level;
        this.chapter = chapter;
    }

    @Override
    public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate subjectPredicate = null;
        Predicate coursePredicate = null;
        Predicate levelPredicate = null;
        Predicate chapterPredicate = null;

        if (subject != null) {
            subjectPredicate = criteriaBuilder.like(root.get("subject"), "%" + subject + "%");
        }

        if (courseId != null) {
            coursePredicate = criteriaBuilder.equal(root.get("course").get("id"), courseId);
        }

        if (level != null) {
            levelPredicate = criteriaBuilder.equal(root.get("level"), level);
        }

        if (chapter != null) {
            chapterPredicate = criteriaBuilder.like(root.get("chapter"), "%" + chapter + "%");
        }

        Predicate finalPredicate = criteriaBuilder.conjunction();

        if (subjectPredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, subjectPredicate);
        }

        if (coursePredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, coursePredicate);
        }

        if (levelPredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, levelPredicate);
        }

        if (chapterPredicate != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, chapterPredicate);
        }

        return finalPredicate;
    }
}
