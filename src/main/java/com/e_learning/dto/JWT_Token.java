package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWT_Token {

    private UserDTO userDTO;
    private String token;
}
