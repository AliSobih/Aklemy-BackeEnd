package com.e_learning.controller;

import com.e_learning.dto.BasicInfoDTO;
import com.e_learning.entities.BasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basicInfo")
@CrossOrigin
@Slf4j
public class BasicInfoController extends BaseController<BasicInfo, BasicInfoDTO>{

}
