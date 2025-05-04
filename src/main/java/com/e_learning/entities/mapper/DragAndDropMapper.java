package com.e_learning.entities.mapper;

import com.e_learning.dao.QuestionDao;
import com.e_learning.dto.DragAndDropDTO;
import com.e_learning.entities.DragAndDrop;
import com.e_learning.entities.Question;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class DragAndDropMapper implements Mapper<DragAndDrop, DragAndDropDTO> {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public DragAndDropDTO toDTO(DragAndDrop entity) {
        DragAndDropDTO dragAndDropDTO = new DragAndDropDTO();
        dragAndDropDTO.setDragItem(entity.getDragItem());
        dragAndDropDTO.setDropItem(entity.getDropItem());
        dragAndDropDTO.setRandomDropItem(entity.getRandomDropItem());
        dragAndDropDTO.setDragItemAr(entity.getDragItemAr());
        dragAndDropDTO.setDropItemAr(entity.getDropItemAr());
        dragAndDropDTO.setRandomDropItemAr(entity.getRandomDropItemAr());
        dragAndDropDTO.setId(entity.getId());
        dragAndDropDTO.setQuestionId(entity.getQuestion().getId());
        return dragAndDropDTO;
    }

    @Override
    public DragAndDrop toEntity(DragAndDropDTO dto) {
        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.setDragItem(dto.getDragItem());
        dragAndDrop.setDropItem(dto.getDropItem());
        dragAndDrop.setRandomDropItem(dto.getRandomDropItem());
        dragAndDrop.setDragItemAr(dto.getDragItemAr());
        dragAndDrop.setDropItemAr(dto.getDropItemAr());
        dragAndDrop.setRandomDropItemAr(dto.getRandomDropItemAr());
        dragAndDrop.setId(dto.getId());

        if (dto.getQuestionId() != null && dto.getQuestionId() != 0) {
            Question question = questionDao.getById(dto.getQuestionId());
            dragAndDrop.setQuestion(question);
            question.addDragAndDrop(dragAndDrop);
        }

        return dragAndDrop;
    }

    @Override
    public ArrayList<DragAndDropDTO> toDTOs(Collection<DragAndDrop> dragAndDrops) {
        return dragAndDrops.stream().map(this::toDTO).collect(toCollection(ArrayList<DragAndDropDTO>::new));
    }

    @Override
    public ArrayList<DragAndDrop> toEntities(Collection<DragAndDropDTO> dragAndDropDTOS) {
        return dragAndDropDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<DragAndDrop>::new));
    }

    @Override
    public PageResult<DragAndDropDTO> toDataPage(PageResult<DragAndDrop> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}