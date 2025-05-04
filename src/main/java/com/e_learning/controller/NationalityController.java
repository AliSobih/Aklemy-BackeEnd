package com.e_learning.controller;

import com.e_learning.dto.NationalityDTO;
import com.e_learning.entities.Nationality;
import com.e_learning.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nationalities")
@CrossOrigin
public class NationalityController extends BaseController<Nationality, NationalityDTO> {
    @Autowired
    private NationalityService nationalityService ;

    @GetMapping("/course/{courseID}")
    public List<NationalityDTO> getNationalitiesByCouseId(@PathVariable Long courseID){
        return nationalityService.getNationalityByCourseId(courseID);
    }
}
