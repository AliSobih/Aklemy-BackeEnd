package com.e_learning.controller;

import com.e_learning.dto.UserDTO;
import com.e_learning.dto.customer.ChangePasswordDTO;
import com.e_learning.entities.User;
import com.e_learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController extends BaseController<User, UserDTO> {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
    public boolean changePassword(@PathVariable(value = "id") Long id, @Valid @RequestBody ChangePasswordDTO dto) {
        return userService.changePassword(dto, id);
    }
}
