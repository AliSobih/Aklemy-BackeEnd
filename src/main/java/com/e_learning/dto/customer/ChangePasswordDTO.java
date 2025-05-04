package com.e_learning.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    String oldPassword ;
    String newPassword;
}
