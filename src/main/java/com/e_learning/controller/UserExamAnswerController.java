package com.e_learning.controller;

import com.e_learning.dto.UserExamAnswerDTO;
import com.e_learning.entities.UserExamAnswers;
import com.e_learning.service.UserExamAnswerService;
import com.e_learning.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user_exam_answers")
@CrossOrigin
@Slf4j
public class UserExamAnswerController extends BaseController<UserExamAnswers, UserExamAnswerDTO> {

    @Autowired
    private UserExamAnswerService userExamAnswerService;

    @PatchMapping(value = "/mark_answers")
    public MessageResponse updateUserExamAnswers(@RequestBody Set<Long> answersIds) {
        userExamAnswerService.updateUserExamAnswers(answersIds);
        return new MessageResponse("Answers has been marked successfully");
    }

    @GetMapping(value = "/getUserAnswers/{userExamQuestionId}")
    public List<UserExamAnswerDTO> getCorrectAnswersById(@PathVariable(value = "userExamQuestionId") Long userExamQuestionId) {
        return userExamAnswerService.getUserExamAnswers(userExamQuestionId);
    }


}
