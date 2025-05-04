package com.e_learning.entities.mapper;

import com.e_learning.dao.UserExamQuestionDao;
import com.e_learning.dto.UserExamAnswerDTO;
import com.e_learning.entities.UserExamAnswers;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserExamAnswerMapper implements Mapper<UserExamAnswers, UserExamAnswerDTO> {

    private final UserExamQuestionDao userExamQuestionDao;

    public UserExamAnswerMapper(UserExamQuestionDao userExamQuestionDao) {
        this.userExamQuestionDao = userExamQuestionDao;
    }

    @Override
    public UserExamAnswerDTO toDTO(UserExamAnswers entity) {
        UserExamAnswerDTO dto = new UserExamAnswerDTO();
        dto.setId(entity.getId());
        dto.setAnswer(entity.getAnswer());
        dto.setAnswerAr(entity.getAnswerAr());
        dto.setMark(entity.getMark());
        dto.setIsCorrect(entity.getIsCorrect());
        dto.setUserExamQuestionId(entity.getUserExamQuestions().getId());

        return dto;
    }

    @Override
    public UserExamAnswers toEntity(UserExamAnswerDTO dto) {
        UserExamAnswers entity = new UserExamAnswers();
        entity.setId(dto.getId());
        entity.setAnswer(dto.getAnswer());
        entity.setAnswerAr(dto.getAnswerAr());
        entity.setMark(dto.getMark());
        entity.setIsCorrect(dto.getIsCorrect());

        Optional<UserExamQuestions> userExamQuestions = userExamQuestionDao.findById(dto.getUserExamQuestionId());
        userExamQuestions.ifPresent(entity::setUserExamQuestions);

        return entity;
    }

    @Override
    public ArrayList<UserExamAnswerDTO> toDTOs(Collection<UserExamAnswers> userExamAnswers) {
        return userExamAnswers.stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<UserExamAnswers> toEntities(Collection<UserExamAnswerDTO> userExamAnswerDTOS) {
        return userExamAnswerDTOS.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PageResult<UserExamAnswerDTO> toDataPage(PageResult<UserExamAnswers> entities) {
        if (entities == null) {
            return null;
        }
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new)),
                entities.getTotalCount(),
                entities.getPageSize(),
                entities.getCurrPage()
        );
    }
}
