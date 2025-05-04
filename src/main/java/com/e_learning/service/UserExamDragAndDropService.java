package com.e_learning.service;

import com.e_learning.dao.UserExamDragAndDropDao;
import com.e_learning.dto.UserExamDragAndDropAnswerDTO;
import com.e_learning.dto.UserExamDragAndDropDTO;
import com.e_learning.entities.UserExamDragAndDrop;
import com.e_learning.entities.mapper.UserExamDragAndDropMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserExamDragAndDropService extends BaseServiceImp<UserExamDragAndDrop> {

    @Autowired
    private UserExamDragAndDropDao userExamDragAndDropDao;

    @Autowired
    private UserExamDragAndDropMapper userExamDragAndDropMapper;

    @Override
    public JpaRepository<UserExamDragAndDrop, Long> Repository() {
        return userExamDragAndDropDao;
    }

    @Transactional
    public void updateUserExamDragAndDrops(Set<UserExamDragAndDropAnswerDTO> dragAndDropAnswerDTOS) {
        Set<Long> dragAndDropsIds = dragAndDropAnswerDTOS.stream()
                .map(UserExamDragAndDropAnswerDTO::getUserExamDragAndDropId)
                .collect(Collectors.toSet());
        List<UserExamDragAndDrop> userExamDragAndDrops = userExamDragAndDropDao.findAllById(dragAndDropsIds);
        if (!userExamDragAndDrops.isEmpty()) {
            // Create a map from the DTO set for easy lookup
            Map<Long, UserExamDragAndDropAnswerDTO> dtoMap = dragAndDropAnswerDTOS.stream()
                    .collect(Collectors.toMap(UserExamDragAndDropAnswerDTO::getUserExamDragAndDropId, dto -> dto));

            userExamDragAndDrops.forEach(userExamDragAndDrop -> {
                userExamDragAndDrop.setMark(true);

                if (dtoMap.containsKey(userExamDragAndDrop.getId())) {
                    UserExamDragAndDropAnswerDTO dto = dtoMap.get(userExamDragAndDrop.getId());
                    if (dto != null) {
                        userExamDragAndDrop.setAnswer(dto.getAnswer());
                        userExamDragAndDrop.setAnswerAr(dto.getAnswerAr());
                    }
                }
            });
            userExamDragAndDropDao.saveAll(userExamDragAndDrops);
        } else {
            throw new RuntimeException("items not found");
        }
    }

    public List<UserExamDragAndDropDTO> getUserExamDragAndDrops(Long userExamQuestionId) {
        List<UserExamDragAndDropDTO> dragAndDropDTOS = new ArrayList<>();
        List<UserExamDragAndDrop> userExamDragAndDrops = userExamDragAndDropDao
                .findAllByUserExamQuestionId(userExamQuestionId);
        if (!userExamDragAndDrops.isEmpty()) {
            dragAndDropDTOS = userExamDragAndDropMapper.toDTOs(userExamDragAndDrops);
        }
        return dragAndDropDTOS;
    }
}
