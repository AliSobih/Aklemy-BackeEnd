package com.e_learning.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="slider")
public class Slider  extends BaseEntity{
    @Column(name="image_url")
    private String imageUrl;

    @Column(name="link")
    private String link;
}
