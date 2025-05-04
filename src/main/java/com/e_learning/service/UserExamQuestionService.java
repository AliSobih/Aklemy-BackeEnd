package com.e_learning.service;

import com.e_learning.dao.UserExamQuestionDao;
import com.e_learning.dto.UserExamQuestionDTO;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.entities.mapper.UserExamQuestionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserExamQuestionService {
    @Autowired
    private UserExamQuestionDao userExamQuestionDao;
    @Autowired
    private UserExamQuestionsMapper userExamQuestionsMapper;
    public void tagQuestion(Long questionId) {
        UserExamQuestions userExamQuestions = userExamQuestionDao.findById(questionId).orElse(null);
        if (userExamQuestions != null) {
            userExamQuestions.setTag(true);
            userExamQuestionDao.save(userExamQuestions);
        }
    }

    public void untagQuestion(Long questionId) {
        UserExamQuestions userExamQuestions = userExamQuestionDao.findById(questionId).orElse(null);
        if (userExamQuestions != null) {
            userExamQuestions.setTag(false);
            userExamQuestionDao.save(userExamQuestions);
        }
    }

    public List<UserExamQuestionDTO> getAllTaggedQuestions(Long examId) {
        return userExamQuestionsMapper.toDTOs(userExamQuestionDao.findByUserExamIdAndTagIsTrue(examId));
    }

}
