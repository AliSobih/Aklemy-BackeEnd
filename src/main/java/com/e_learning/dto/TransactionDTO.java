package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionDTO extends BaseDTO {
    private Date transactionDate;
    private Double amount;
    private Double discount;
    private String paymentMethod;
    private UserDTO user;
    private CourseDTO course;
}
