package com.e_learning.entities.mapper;

import com.e_learning.dao.QuestionDao;
import com.e_learning.dao.UserExamDao;
import com.e_learning.dto.UserExamQuestionDTO;
import com.e_learning.entities.Question;
import com.e_learning.entities.UserExam;
import com.e_learning.entities.UserExamQuestions;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserExamQuestionsMapper implements Mapper<UserExamQuestions, UserExamQuestionDTO> {

    private final UserExamAnswerMapper userExamAnswerMapper;
    private final UserExamDao userExamDao;
    private final QuestionDao questionDao;
    private final UserExamDragAndDropMapper userExamDragAndDropMapper;

    public UserExamQuestionsMapper(UserExamAnswerMapper userExamAnswerMapper, UserExamDao userExamDao, QuestionDao questionDao,
                                   UserExamDragAndDropMapper userExamDragAndDropMapper) {
        this.userExamAnswerMapper = userExamAnswerMapper;
        this.userExamDao = userExamDao;
        this.questionDao = questionDao;
        this.userExamDragAndDropMapper = userExamDragAndDropMapper;
    }

    @Override
    public UserExamQuestionDTO toDTO(UserExamQuestions entity) {
        UserExamQuestionDTO dto = new UserExamQuestionDTO();
        dto.setId(entity.getId());
        dto.setQuestionText(entity.getQuestion().getQuestionText());
        dto.setQuestionTextAr(entity.getQuestion().getQuestionTextAr());
        dto.setStatus(entity.getQuestion().getStatus());
        dto.setIsCorrect(entity.getIsCorrect());
        dto.setTag(entity.getTag());
        dto.setImagePath(entity.getImagePath());
        dto.setUserExamId(entity.getUserExam().getId());
        dto.setQuestionId(entity.getQuestion().getId());

        if (entity.getAnswers() != null && !entity.getAnswers().isEmpty()) {
            dto.setUserExamAnswers(new HashSet<>(userExamAnswerMapper.toDTOs(entity.getAnswers())));
        }
        if (entity.getDragAndDrops() != null && !entity.getDragAndDrops().isEmpty()) {
            dto.setUserExamDragAndDrops(new HashSet<>(userExamDragAndDropMapper.toDTOs(entity.getDragAndDrops())));
        }

        return dto;
    }

    @Override
    public UserExamQuestions toEntity(UserExamQuestionDTO dto) {
        UserExamQuestions entity = new UserExamQuestions();
        entity.setId(dto.getId());
        entity.setIsCorrect(dto.getIsCorrect());
        entity.setTag(dto.getTag());

        Optional<UserExam> userExam = userExamDao.findById(dto.getUserExamId());
        userExam.ifPresent(entity::setUserExam);

        Optional<Question> question = questionDao.findById(dto.getQuestionId());
        question.ifPresent(entity::setQuestion);

        if (dto.getUserExamAnswers() != null && !dto.getUserExamAnswers().isEmpty()) {
            entity.setAnswers(new HashSet<>(userExamAnswerMapper.toEntities(dto.getUserExamAnswers())));
        }
        if (dto.getUserExamDragAndDrops() != null && !dto.getUserExamDragAndDrops().isEmpty()) {
            entity.setDragAndDrops(new HashSet<>(userExamDragAndDropMapper.toEntities(dto.getUserExamDragAndDrops())));
        }

        return entity;
    }

    @Override
    public ArrayList<UserExamQuestionDTO> toDTOs(Collection<UserExamQuestions> userExamQuestions) {
        return userExamQuestions.stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<UserExamQuestions> toEntities(Collection<UserExamQuestionDTO> userExamQuestionDTOS) {
        return userExamQuestionDTOS.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PageResult<UserExamQuestionDTO> toDataPage(PageResult<UserExamQuestions> entities) {
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
