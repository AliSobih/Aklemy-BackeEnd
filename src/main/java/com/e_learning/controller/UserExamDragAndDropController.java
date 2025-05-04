package com.e_learning.controller;

import com.e_learning.dto.UserExamDragAndDropAnswerDTO;
import com.e_learning.dto.UserExamDragAndDropDTO;
import com.e_learning.entities.UserExamDragAndDrop;
import com.e_learning.service.UserExamDragAndDropService;
import com.e_learning.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user_exam_drag_and_drops")
@CrossOrigin
@Slf4j
public class UserExamDragAndDropController extends BaseController<UserExamDragAndDrop, UserExamDragAndDropDTO> {

    @Autowired
    private UserExamDragAndDropService userExamDragAndDropService;

    @PatchMapping(value = "/mark_drag_and_drops")
    public MessageResponse updateUserExamDragAndDrop(@RequestBody Set<UserExamDragAndDropAnswerDTO> dragAndDropAnswersDTOs) {
        userExamDragAndDropService.updateUserExamDragAndDrops(dragAndDropAnswersDTOs);
        return new MessageResponse("Answers has been marked successfully");
    }

    @GetMapping(value = "/getUserAnswers/{userExamQuestionId}")
    public List<UserExamDragAndDropDTO> getCorrectAnswersById(@PathVariable(value = "userExamQuestionId") Long userExamQuestionId) {
        return userExamDragAndDropService.getUserExamDragAndDrops(userExamQuestionId);
    }
}
