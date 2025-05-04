package com.e_learning.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "nationality")
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Nationality extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "currency")
    private String currency;

    @Column(name = "factor")
    private Double factor;

    @Column(name = "rate_exchange")
    private Double rateExchange;

//     for arabic
    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "currency_ar")
    private String currencyAr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "country_code")
    private String countryCode;
}
