package com.e_learning.controller;

import com.e_learning.dto.ExamDTO;
import com.e_learning.entities.Exam;
import com.e_learning.service.ExamService;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin
@Slf4j
public class ExamController extends BaseController<Exam, ExamDTO> {

    @Autowired
    private ExamService examService;


    @PostMapping("/courseExams/{courseId}")
    public PageResult<ExamDTO> getCourseExamsByCourseId(@PathVariable Long courseId, @RequestBody PageQueryUtil pageUtil) {
        return examService.getCourseExamByCourseId(courseId, pageUtil);
    }


    @GetMapping("/courseExam/{courseId}")
    public List<ExamDTO> getCourseExamsByCourseId(@PathVariable Long courseId) {
        return examService.getCourseExamByCourseId(courseId);
    }

    @GetMapping("/courseExam/{courseId}/{sectionId}")
    public List<ExamDTO> getCourseExamsByCourseId(@PathVariable Long courseId, @PathVariable Long sectionId) {
        return examService.getCourseExamBySectionId(courseId, sectionId);
    }

    @GetMapping("/allExams/{courseId}")
    public List<ExamDTO> getCourseExams(@PathVariable Long courseId) {
        return examService.getCourseExams(courseId);
    }
}