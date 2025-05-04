package com.e_learning.service;

import com.e_learning.dao.UserExamAnswerDao;
import com.e_learning.dto.UserExamAnswerDTO;
import com.e_learning.entities.UserExamAnswers;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.entities.mapper.UserExamAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserExamAnswerService extends BaseServiceImp<UserExamAnswers> {

    @Autowired
    private UserExamAnswerDao userExamAnswerDao;

    @Autowired
    private UserExamAnswerMapper userExamAnswerMapper;

    @Override
    public JpaRepository<UserExamAnswers, Long> Repository() {
        return userExamAnswerDao;
    }

    @Transactional
    public void updateUserExamAnswers(Set<Long> answersIds) {
        List<UserExamAnswers> userExamAnswers = userExamAnswerDao.findAllById(answersIds);
        if (!userExamAnswers.isEmpty()) {
            Set<UserExamQuestions> userExamQuestions = userExamAnswers.stream()
                    .map(UserExamAnswers::getUserExamQuestions)
                    .collect(Collectors.toSet());

            // set all other answers mark flag with false (not selected)
            userExamQuestions.forEach(userExamQuestion -> {
                Set<UserExamAnswers> answers = userExamQuestion.getAnswers();
                answers.forEach(answer -> {
                    answer.setMark(false);
                });
                userExamAnswerDao.saveAll(answers);
            });

            // set new answers mark flag with true (selected)
            userExamAnswers.forEach(userExamAnswer -> {
                userExamAnswer.setMark(true);
            });
            userExamAnswerDao.saveAll(userExamAnswers);
        } else {
            throw new RuntimeException("items not found");
        }
    }

    public List<UserExamAnswerDTO> getUserExamAnswers(Long userExamQuestionId) {
        List<UserExamAnswerDTO> userExamAnswerDTOS = new ArrayList<>();
        List<UserExamAnswers> userExamAnswers = userExamAnswerDao.findAllByUserExamQuestionsId(userExamQuestionId);
        if (!userExamAnswers.isEmpty()) {
            userExamAnswerDTOS = userExamAnswerMapper.toDTOs(userExamAnswers);
        }

        return userExamAnswerDTOS;
    }

}
