package com.e_learning.controller;

import com.e_learning.dto.UserExamDTO;
import com.e_learning.entities.UserExam;
import com.e_learning.entities.mapper.UserExamMapper;
import com.e_learning.service.UserExamService;
import com.e_learning.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userexams")
@CrossOrigin
@Slf4j
public class UserExamController extends BaseController<UserExam, UserExamDTO> {

    @Autowired
    private UserExamMapper userExamMapper;

    @Autowired
    private UserExamService userExamService;

    @PostMapping(value = "/start_exam")
    public UserExamDTO startUserExam(@RequestBody UserExamDTO userExamDTO) {
        return userExamService.startUserExam(userExamDTO);
    }

    @PatchMapping(value = "/mark_exam/{userExamId}")
    public UserExamDTO markUserExam(@PathVariable(value = "userExamId") Long userExamId) {
        return userExamService.markUserExam(userExamId);
    }

    @PatchMapping(value = "/pause/{userExamId}/{remainingTime}")
    public MessageResponse pause(@PathVariable(value = "userExamId") Long userExamId, @PathVariable(value = "remainingTime")Integer remainingTime){
        userExamService.updateRemainingTime(userExamId,remainingTime);
        return new MessageResponse("Time has been updated successfully");
    }

    @GetMapping(value = "/paused_exams/{userId}")
    public List<UserExamDTO> getAllPausedUserExams(@PathVariable(value = "userId") Long userId) {
        return userExamService.getAllPausedUserExams(userId);
    }
}
