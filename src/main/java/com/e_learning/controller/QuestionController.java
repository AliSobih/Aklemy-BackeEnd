package com.e_learning.controller;

import com.e_learning.dao.QuestionDao;
import com.e_learning.dto.QuestionCorrectAnswersDTO;
import com.e_learning.dto.QuestionDTO;
import com.e_learning.dto.requestDTO.QuestionSearchRequestDTO;
import com.e_learning.entities.Question;
import com.e_learning.entities.mapper.QuestionMapper;
import com.e_learning.service.QuestionService;
import com.e_learning.service.StorageServiceOffline;
import com.e_learning.util.Constants;
import com.e_learning.util.MessageResponse;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin
@Slf4j
public class QuestionController extends BaseController<Question, QuestionDTO> {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StorageServiceOffline storageServiceOffline;

    @PostMapping("/addAll")
    public MessageResponse addAllQuestions(@RequestBody Collection<QuestionDTO> questionDTOS) {
        questionDao.saveAll(questionMapper.toEntities(questionDTOS));
        return new MessageResponse("Item has been saved successfully");
    }

    @PostMapping("/course/{courseId}")
    public PageResult<QuestionDTO> getQuestionsByCourseId(@PathVariable Long courseId,
                                                          @RequestBody PageQueryUtil pageUtil) {
        return questionService.getQuestionsByCourseId(courseId, pageUtil);
    }

    @RequestMapping(value = "/search/{pageNumber}/{size}", method = RequestMethod.POST)
    public ResponseEntity<PageResult<QuestionDTO>> searchQuestions(@PathVariable int pageNumber,
                                                                   @PathVariable int size,
                                                                   @RequestBody QuestionSearchRequestDTO questionRequestDTO) {
        PageQueryUtil pageUtil = new PageQueryUtil(pageNumber, size);
        return new ResponseEntity<>(questionService.searchQuestions(pageUtil, questionRequestDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/getCorrectAnswers/{questionId}", method = RequestMethod.GET)
    public QuestionCorrectAnswersDTO getCorrectAnswersById(@PathVariable(value = "questionId") Long questionId) {
        return questionService.getCorrectAnswersById(questionId);
    }

    @PostMapping(value = "/uploadImage")
    public ResponseEntity<MessageResponse> uploadQuestionImage(@RequestParam("file") MultipartFile file) {
        this.storageServiceOffline.uploadFile(List.of(file), Constants.QUESTION_IMAGE);
        return new ResponseEntity<>(new MessageResponse("Images has been uploaded"), HttpStatus.OK);
    }

    @GetMapping(value = "/downloadImage/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable String fileName) {
        try {
            if (!fileName.equals("null")) {
                byte[] data = storageServiceOffline.downloadFile(fileName, Constants.QUESTION_IMAGE);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return null;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

}
