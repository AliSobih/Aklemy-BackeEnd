package com.e_learning.controller;

import com.e_learning.dto.WatchedLessonDTO;
import com.e_learning.dto.WatchedLessonsPerSection;
import com.e_learning.entities.WatchedLesson;
import com.e_learning.service.WatchedLessonService;
import com.e_learning.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/watched-lessons")
@CrossOrigin
public class WatchedLessonController extends BaseController<WatchedLesson, WatchedLessonDTO> {

    @Autowired
    private WatchedLessonService service;

    @GetMapping("watched-per-course/{courseId}/{userId}")
    public List<WatchedLessonsPerSection> getWatchedLessonsPerSections(@PathVariable(value = "courseId") Long courseId,
                                                                       @PathVariable(value = "userId") Long userId) {
        return service.getWatchedLessonsPerSections(courseId, userId);
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public MessageResponse create(@Valid @RequestBody WatchedLessonDTO watchedLessonDTO) {
        service.save(watchedLessonDTO);
        return new MessageResponse("Item has been saved successfully");
    }
}
