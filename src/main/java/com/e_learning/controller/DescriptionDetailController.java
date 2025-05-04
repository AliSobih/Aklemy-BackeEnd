package com.e_learning.controller;

import com.e_learning.dto.DescriptionDetailDTO;
import com.e_learning.entities.DescriptionDetail;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/description_detail")
@CrossOrigin
public class DescriptionDetailController extends BaseController<DescriptionDetail, DescriptionDetailDTO> {
}
