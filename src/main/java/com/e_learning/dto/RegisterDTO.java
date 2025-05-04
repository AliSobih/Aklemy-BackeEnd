package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class RegisterDTO {

    @Email(message = "enter email")
    @NotBlank(message = "enter email")
    private String email;

    @NotBlank(message = "enter name")
    private String password;

    @NotBlank(message = "enter password")
    private String name;

//    @NotBlank(message = "enter number in arabic")
    private String nameAr;

    public RegisterDTO(String email, String password, String name, String nameAr) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nameAr = nameAr;
    }
}
