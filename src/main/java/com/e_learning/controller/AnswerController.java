package com.e_learning.controller;

import com.e_learning.dto.AnswerDTO;
import com.e_learning.entities.Answer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@CrossOrigin
public class AnswerController extends BaseController<Answer, AnswerDTO>{

}
