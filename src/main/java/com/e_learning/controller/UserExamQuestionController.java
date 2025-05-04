package com.e_learning.controller;

import com.e_learning.service.UserExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_exam_question")
@CrossOrigin
public class UserExamQuestionController {

    @Autowired
    private UserExamQuestionService userExamQuestionService;

    @PatchMapping(value = "/tag")
    public void tagQuestion(@RequestBody Long questionId) {
        userExamQuestionService.tagQuestion(questionId);
    }


    @PatchMapping(value = "/untag")
    public void untagQuestion(@RequestBody Long questionId) {
        userExamQuestionService.untagQuestion(questionId);
    }

//    @GetMapping("/all-tagged/{examId}")
//    public List<UserExamQuestionDTO> getALlTaggedQuestion(@PathVariable Long examId) {
//        return userExamQuestionService.getAllTaggedQuestions(examId);
//    }
}
