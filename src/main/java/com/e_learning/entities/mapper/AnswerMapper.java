package com.e_learning.entities.mapper;

import com.e_learning.dao.QuestionDao;
import com.e_learning.dto.AnswerDTO;
import com.e_learning.entities.Answer;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AnswerMapper implements Mapper<Answer, AnswerDTO> {

    @Autowired
    private QuestionDao questionDao ;

    @Override
    public AnswerDTO toDTO(Answer entity) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(entity.getId());
        answerDTO.setAnswer(entity.getAnswer());
        answerDTO.setAnswerAr(entity.getAnswerAr());
        answerDTO.setIsCorrect(entity.getIsCorrect());
        answerDTO.setQuestionId(entity.getQuestion().getId());
        answerDTO.setDescription(entity.getDescription());
        answerDTO.setDescriptionAr(entity.getDescriptionAr());
        return answerDTO ;
    }

    @Override
    public Answer toEntity(AnswerDTO dto) {
        Answer answer = new Answer();
        answer.setId(dto.getId());
        answer.setAnswer(dto.getAnswer());
        answer.setAnswerAr(dto.getAnswerAr());
        answer.setIsCorrect(dto.getIsCorrect());
        answer.setDescription(dto.getDescription());
        answer.setDescriptionAr(dto.getDescriptionAr());
        if(dto.getQuestionId()!=null&& dto.getQuestionId()!=0) {
            answer.setQuestion(questionDao.getById(dto.getQuestionId()));
        }
        return answer ;
    }

    @Override
    public ArrayList<AnswerDTO> toDTOs(Collection<Answer> answers) {
        return answers.stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList<AnswerDTO>::new));
    }

    @Override
    public ArrayList<Answer> toEntities(Collection<AnswerDTO> answerDTOS) {
        return answerDTOS.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList<Answer>::new));
    }

    @Override
    public PageResult<AnswerDTO> toDataPage(PageResult<Answer> entities) {
        return new PageResult<>(toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
