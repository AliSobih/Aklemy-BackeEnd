package com.e_learning.controller;

import com.e_learning.dto.LessonDTO;
import com.e_learning.entities.Lesson;
import com.e_learning.service.LessonService;
import com.e_learning.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
@CrossOrigin
public class LessonController extends BaseController<Lesson, LessonDTO> {

    @Autowired
    private LessonService lessonService;

    @PatchMapping("/updateLesson/{lessonId}")
    public MessageResponse updateLesson(@PathVariable(value = "lessonId") Long lessonId, @RequestBody LessonDTO dto) {
        lessonService.updateLesson(lessonId, dto);
        return new MessageResponse("Item has been updated successfully");
    }

}
