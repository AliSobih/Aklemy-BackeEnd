package com.e_learning.controller;


import com.e_learning.dto.RegisterDTO;
import com.e_learning.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Boolean> register(@Valid @RequestBody RegisterDTO registerDTO) {
         String s = registrationService.register(registerDTO);
         return  new ResponseEntity<>(s != null, HttpStatus.OK);

    }

    @GetMapping(path = "confirm")
    public boolean confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

//    @GetMapping(value = "/getScreens/{roleId}")
//    public ResponseEntity<List<String>> getScreens(@PathVariable long roleId) {
//        return new ResponseEntity<>(registrationService.getScreens(roleId), HttpStatus.OK);
//    }
}
