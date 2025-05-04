package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "course")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Course extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "price")
    private Double price;

    @Column(name = "fixed_price")
    private Double fixedPrice;

    @Column(name = "approved ")
    private Boolean approved = false;

    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "description_ar", columnDefinition = "LONGTEXT")
    private String descriptionAr;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    private Set<Section> sections = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<DescriptionMaster> descriptionMasters = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Nationality> nationalities = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Exam> exams = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Question> questions = new HashSet<>();

    // Convenience methods
    public void addSection(Section section) {
        sections.add(section);
        section.setCourse(this);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setCourse(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setCourse(this);
    }

    public void addDescriptionMaster(DescriptionMaster descriptionMaster) {
        descriptionMasters.add(descriptionMaster);
        descriptionMaster.setCourse(this);
    }

    public void addNationality(Nationality nationality) {
        this.nationalities.add(nationality);
        nationality.setCourse(this);
    }

    public void removeNationality(Nationality nationality) {
        this.nationalities.remove(nationality);
        nationality.setCourse(null);
    }

    public void addExam(Exam exam) {
        exams.add(exam);
        exam.setCourse(this);
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setCourse(this);
    }
}
