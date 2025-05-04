package com.e_learning.controller;

import com.e_learning.dao.RoleDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.JWT_Token;
import com.e_learning.dto.LoginDTO;
import com.e_learning.dto.ResetPasswordDTO;
import com.e_learning.dto.UserDTO;
import com.e_learning.entities.Role;
import com.e_learning.entities.User;
import com.e_learning.entities.mapper.UserMapper;
import com.e_learning.security.config.JwtResponse;
import com.e_learning.security.config.TokenUtil;
import com.e_learning.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

;


@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class LoginController {

    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper ;
    @Autowired
    private RoleDao roleDao;

    @PostMapping("/sign-in")
    public ResponseEntity<JWT_Token> login(@Valid  @RequestBody LoginDTO loginDTO) {
       final Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(loginDTO.getUsername());
        UserDTO userDTO = userService.findByEmail(loginDTO.getUsername());
        String token = tokenUtil.generateToken(userDetails);
        JwtResponse response = new JwtResponse(token);
        JWT_Token jwt_token = new JWT_Token();
        jwt_token.setToken(response.getToken());
        jwt_token.setUserDTO(userDTO);
        return new ResponseEntity<>(jwt_token, HttpStatus.OK);
    }


    @PostMapping("/facebook")
    public ResponseEntity<JWT_Token> loginWithFacebook(@RequestBody JWT_Token tokenDto) throws Exception {
        if(tokenDto.getToken()!=null){
            Facebook facebook = new FacebookTemplate(tokenDto.getToken());
            String [] data = {"email"};
            org.springframework.social.facebook.api.User user =
                    facebook.fetchObject("me", org.springframework.social.facebook.api.User.class,data);
            String email = user.getEmail();
            String name= user.getName();
            UserDTO userDTO = userService.findByEmail(email);
            if(userDTO == null){
                userDTO = new UserDTO();
                userDTO.setName(name);
                userDTO.setEmail(email);
                User userMapperEntity  = userMapper.toEntity(userDTO);
                userMapperEntity.setEnabled(true);

                Role role = null;
                if (!roleDao.existsByName("ROLE_STUDENT")) {
                    role = new Role("ROLE_STUDENT", "طالب");
                    roleDao.save(role);
                } else {
                    role = roleDao.findByName("ROLE_STUDENT");
                }

                userMapperEntity.setRole(role);
                User savedUser = userService.save(userMapperEntity);
                userDTO = userMapper.toDTO(savedUser);
            }
            UserDetails userDetails = userService.loadUserByUsername(email);
            String returnToken = tokenUtil.generateToken(userDetails);
            JwtResponse response = new JwtResponse(returnToken);
            JWT_Token jwt_token = new JWT_Token();
            jwt_token.setToken(response.getToken());
            jwt_token.setUserDTO(userDTO);
            return new ResponseEntity<>(jwt_token, HttpStatus.OK);
        }
        else {
            return new  ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/create-reset-password-token/{email}")
    public boolean createPasswordResetToken(@PathVariable String email) {
        return userService.createPasswordResetToken(email);
    }

    @GetMapping("/conform-reset-password-token")
    public boolean conformResetPassword(@RequestParam String token) {
        return userService.conformResetPassword(token);
    }

    @PostMapping("/reset-password")
    public boolean resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword(resetPasswordDTO);
    }

}
