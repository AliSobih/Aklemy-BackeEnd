package com.e_learning.entities.mapper;

import com.e_learning.dao.UserExamQuestionDao;
import com.e_learning.dto.UserExamDragAndDropDTO;
import com.e_learning.entities.UserExamDragAndDrop;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserExamDragAndDropMapper implements Mapper<UserExamDragAndDrop, UserExamDragAndDropDTO> {

    private final UserExamQuestionDao userExamQuestionDao;

    public UserExamDragAndDropMapper(UserExamQuestionDao userExamQuestionDao) {
        this.userExamQuestionDao = userExamQuestionDao;
    }

    @Override
    public UserExamDragAndDropDTO toDTO(UserExamDragAndDrop entity) {
        UserExamDragAndDropDTO dto = new UserExamDragAndDropDTO();
        dto.setId(entity.getId());
        dto.setDragItem(entity.getDragItem());
        dto.setDropItem(entity.getDropItem());
        dto.setRandomDropItem(entity.getRandomDropItem());
        dto.setDragItemAr(entity.getDragItemAr());
        dto.setDropItemAr(entity.getDropItemAr());
        dto.setRandomDropItemAr(entity.getRandomDropItemAr());
        dto.setMark(entity.getMark());
        dto.setAnswer(entity.getAnswer());
        dto.setAnswerAr(entity.getAnswerAr());
        dto.setUserExamQuestionId(entity.getUserExamQuestion().getId());

        return dto;
    }

    @Override
    public UserExamDragAndDrop toEntity(UserExamDragAndDropDTO dto) {
        UserExamDragAndDrop entity = new UserExamDragAndDrop();
        entity.setId(dto.getId());
        entity.setDragItem(dto.getDragItem());
        entity.setDropItem(dto.getDropItem());
        entity.setRandomDropItem(dto.getRandomDropItem());
        entity.setDragItemAr(dto.getDragItemAr());
        entity.setDropItemAr(dto.getDropItemAr());
        entity.setRandomDropItemAr(dto.getRandomDropItemAr());
        entity.setMark(dto.getMark());
        entity.setAnswer(dto.getAnswer());
        entity.setAnswerAr(dto.getAnswerAr());

        Optional<UserExamQuestions> userExamQuestions = userExamQuestionDao.findById(dto.getUserExamQuestionId());
        userExamQuestions.ifPresent(entity::setUserExamQuestion);

        return entity;
    }

    @Override
    public ArrayList<UserExamDragAndDropDTO> toDTOs(Collection<UserExamDragAndDrop> userExamDragAndDrops) {
        return userExamDragAndDrops.stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<UserExamDragAndDrop> toEntities(Collection<UserExamDragAndDropDTO> userExamDragAndDropDTOS) {
        return userExamDragAndDropDTOS.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PageResult<UserExamDragAndDropDTO> toDataPage(PageResult<UserExamDragAndDrop> entities) {
        if (entities == null) {
            return null;
        }
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new)),
                entities.getTotalCount(),
                entities.getPageSize(),
                entities.getCurrPage());
    }
}
