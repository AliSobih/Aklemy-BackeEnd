package com.e_learning.service;

import com.e_learning.dao.QuestionDao;
import com.e_learning.dao.UserExamDao;
import com.e_learning.dto.UserExamDTO;
import com.e_learning.entities.UserExam;
import com.e_learning.entities.UserExamAnswers;
import com.e_learning.entities.UserExamDragAndDrop;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.entities.mapper.QuestionMapper;
import com.e_learning.entities.mapper.UserExamMapper;
import com.e_learning.entities.mapper.UserExamQuestionsMapper;
import com.e_learning.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserExamService extends BaseServiceImp<UserExam> {

    @Autowired
    private UserExamDao userExamDao;

    @Autowired
    private UserExamMapper userExamMapper;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserExamQuestionsMapper userExamQuestionsMapper;

    @Override
    public JpaRepository<UserExam, Long> Repository() {
        return userExamDao;
    }

    public UserExamDTO markUserExam(Long userExamId) {
        UserExam userExam = findById(userExamId);
        UserExamDTO userExamDTO = null;
        if (userExam != null) {
            AtomicInteger correctAnswers = new AtomicInteger();
            AtomicInteger wrongAnswers = new AtomicInteger();

            Set<UserExamQuestions> userExamQuestions = userExam.getExamQuestions();
            userExamQuestions.forEach(userExamQuestion -> {
                boolean isCorrect = false;

                Set<UserExamAnswers> userExamAnswers = userExamQuestion.getAnswers();
                if (userExamAnswers != null && !userExamAnswers.isEmpty()) {
                    isCorrect = markAnswers(userExamAnswers, userExamQuestion.getStatus());
                }

                Set<UserExamDragAndDrop> userExamDragAndDrops = userExamQuestion.getDragAndDrops();
                if (userExamDragAndDrops != null && !userExamDragAndDrops.isEmpty()) {
                    isCorrect = markDragAndDrops(userExamDragAndDrops, userExam.getLanguage());
                }
                userExamQuestion.setIsCorrect(isCorrect);
                if (isCorrect) {
                    correctAnswers.incrementAndGet();
                } else {
                    wrongAnswers.incrementAndGet();
                }
            });

            userExam.setCorrectAnswers(correctAnswers.get());
            userExam.setWrongAnswers(wrongAnswers.get());
            userExam.setStatus(true);
            userExam.setNetTime(userExam.getExam().getTime() - userExam.getRemainingTime());

            userExamDao.save(userExam);

            userExamDTO = userExamMapper.toDTO(userExam);
        }
        return userExamDTO;
    }

    private boolean markAnswers(Set<UserExamAnswers> userExamAnswers, String status) {
        boolean isCorrect = false;
        if (Constants.QUESTION_STATUS_CHOICE.equalsIgnoreCase(status)) {
            isCorrect = markSingleAnswers(userExamAnswers);
        }
        if (Constants.QUESTION_STATUS_MULTIPLE_CHOICE.equalsIgnoreCase(status)) {
            isCorrect = markMultipleAnswers(userExamAnswers);
        }
        return isCorrect;
    }

    private boolean markSingleAnswers(Set<UserExamAnswers> userExamAnswers) {
        for (UserExamAnswers answer : userExamAnswers) {
            if (answer.getIsCorrect() && answer.getMark()) {
                return true;
            }
        }
        return false;
    }

    private boolean markMultipleAnswers(Set<UserExamAnswers> userExamAnswers) {
        for (UserExamAnswers answer : userExamAnswers) {
            if ((answer.getIsCorrect() && !answer.getMark()) || (!answer.getIsCorrect() && answer.getMark())) {
                return false; // If an answer is correct but not marked, the question is not correctly answered
            }
        }
        return true; // All correct answers are marked
    }

    private boolean markDragAndDrops(Set<UserExamDragAndDrop> userExamDragAndDrops, String locale) {
        for (UserExamDragAndDrop item : userExamDragAndDrops) {
            boolean isCorrect;
            if ("ar".equalsIgnoreCase(locale)) {
                isCorrect = item.getAnswerAr().equalsIgnoreCase(item.getDropItemAr());
            } else {
                isCorrect = item.getAnswer().equalsIgnoreCase(item.getDropItem());
            }

            if (!isCorrect) {
                // If any item is incorrect, the question is not correctly answered
                return false;
            }
        }
        return true;
    }

    public List<UserExamDTO> getAllPausedUserExams(Long userId) {
        List<UserExamDTO> userExamDTOS = new ArrayList<>();
        List<UserExam> userExams = userExamDao.findAllByPausedByUserId(userId);
        if (!userExams.isEmpty()) {
            userExamDTOS = userExamMapper.toDTOs(userExams);
        }
        return userExamDTOS;
    }

    public void updateRemainingTime(Long userExamId, Integer remainingTime) {
        Optional<UserExam> userExam = userExamDao.findById(userExamId);
        if (userExam.isPresent()) {
            UserExam foundUserExam = userExam.get();
            foundUserExam.setRemainingTime(remainingTime);
            userExamDao.save(foundUserExam);
        }
    }

    public UserExamDTO startUserExam(UserExamDTO userExamDTO) {
        UserExam userExam = userExamDao.findPausedUserExam(userExamDTO.getExamId(), userExamDTO.getUserId());
        if (userExam == null) {
            userExam = this.save(userExamMapper.toEntity(userExamDTO));
        }
        return userExamMapper.toDTO(userExam);
    }
}
