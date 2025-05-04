package com.e_learning.entities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "basic_info")
public class BasicInfo extends BaseEntity {
    @Column(name="whatsapp")
    private String whatsApp;
    @Column(name = "phone")
    private String phone ;
    @Column(name = "instagram")
    private String instagram;
    @Column(name = "twitter")
    private String twitter ;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "tiktok")
    private String tiktok;
    @Column(name = "messenger")
    private String messenger ;
    @Column(name = "telegram")
    private String telegram;
    @Column(name = "youtube")
    private String youtube;
}
