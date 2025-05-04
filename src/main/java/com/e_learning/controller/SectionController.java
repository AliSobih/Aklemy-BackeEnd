package com.e_learning.controller;

import com.e_learning.dto.SectionDTO;
import com.e_learning.entities.Section;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sections")
@CrossOrigin
public class SectionController extends BaseController<Section, SectionDTO> {
}
