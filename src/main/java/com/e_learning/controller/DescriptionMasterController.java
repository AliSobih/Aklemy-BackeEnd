package com.e_learning.controller;

import com.e_learning.dto.DescriptionMasterDTO;
import com.e_learning.entities.DescriptionMaster;
import com.e_learning.service.DescriptionMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/description_master")
@CrossOrigin
public class DescriptionMasterController extends BaseController<DescriptionMaster, DescriptionMasterDTO> {
    private DescriptionMasterService  descriptionMasterService;
    @Autowired
    public DescriptionMasterController(DescriptionMasterService descriptionMasterService){
        this.descriptionMasterService = descriptionMasterService ;
    }
    @GetMapping("/course/{courseId}")
    public List<DescriptionMasterDTO> getCoursesByCategoryId(@PathVariable Long courseId ) {
        return descriptionMasterService.getDescriptionByCourseId(courseId );
    }
}
