package com.e_learning.controller;

import com.e_learning.dto.RoleDTO;
import com.e_learning.entities.Role;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@CrossOrigin
public class RoleController extends BaseController<Role, RoleDTO> {
}
