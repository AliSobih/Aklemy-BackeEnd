package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "category")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Category extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
//    TODO: for arabic
    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "description_ar", columnDefinition = "LONGTEXT")
    private String descriptionAr;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private Set<Course> courses;

//    convenience method

    public void addCourse(Course course) {
        courses.add(course);
    }
}
