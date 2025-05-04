package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "drag_and_drop")
@NoArgsConstructor
public class DragAndDrop extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "drag_item", nullable = false, columnDefinition = "LONGTEXT")
    private String dragItem;

    @Column(name = "drop_item", columnDefinition = "LONGTEXT")
    private String dropItem;

    @Column(name = "drag_item_ar", nullable = false, columnDefinition = "LONGTEXT")
    private String dragItemAr;

    @Column(name = "drop_item_ar", columnDefinition = "LONGTEXT")
    private String dropItemAr;

    @Column(name="random_drop_item", columnDefinition = "LONGTEXT")
    private String randomDropItem ;

    @Column(name="random_drop_item_ar", columnDefinition = "LONGTEXT")
    private String randomDropItemAr ;

}
