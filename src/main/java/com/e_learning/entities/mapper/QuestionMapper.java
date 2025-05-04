package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.ExamDao;
import com.e_learning.dto.AnswerDTO;
import com.e_learning.dto.DragAndDropDTO;
import com.e_learning.dto.QuestionDTO;
import com.e_learning.entities.Answer;
import com.e_learning.entities.DragAndDrop;
import com.e_learning.entities.Question;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

@Component
public class QuestionMapper implements Mapper<Question, QuestionDTO> {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private ExamDao examDao;
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private DragAndDropMapper dragAndDropMapper;

    @Override
    public QuestionDTO toDTO(Question entity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(entity.getId());
        questionDTO.setSubject(entity.getSubject());
        questionDTO.setQuestion(entity.getQuestionText());
        questionDTO.setId(entity.getId());
        questionDTO.setQuestionAr(entity.getQuestionTextAr());
        questionDTO.setStatus(entity.getStatus());
        questionDTO.setLevel(entity.getLevel());
        questionDTO.setChapter(entity.getChapter());
        questionDTO.setImagePath(entity.getImagePath());
        questionDTO.setCourseId(entity.getCourse().getId());
        Set<AnswerDTO> answerDTOS = new HashSet<>(answerMapper.toDTOs(entity.getAnswers()));
        questionDTO.setAnswers(answerDTOS);
        Set<DragAndDropDTO> dragAndDropDTOS = new HashSet<>(dragAndDropMapper.toDTOs(entity.getDragAndDrops()));
        questionDTO.setDragAndDrops(dragAndDropDTOS);
        return questionDTO;
    }

    @Override
    public Question toEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setQuestionText(dto.getQuestion());
        question.setQuestionTextAr(dto.getQuestionAr());
        question.setLevel(dto.getLevel());
        question.setStatus(dto.getStatus());
        question.setSubject(dto.getSubject());
        question.setChapter(dto.getChapter());
        question.setImagePath(dto.getImagePath());
        if (dto.getCourseId() != null && dto.getCourseId() != 0) {
            question.setCourse(courseDao.getById(dto.getCourseId()));
        }

        if (dto.getAnswers() != null && !dto.getAnswers().isEmpty()) {
            Set<Answer> answers = new HashSet<>(answerMapper.toEntities(dto.getAnswers()));
            answers.forEach(answer -> {
                answer.setQuestion(question);
                question.addAnswer(answer);
            });
            question.setAnswers(answers);
        }

        if (dto.getDragAndDrops() != null && !dto.getDragAndDrops().isEmpty()) {
            Set<DragAndDrop> dragAndDrops = new HashSet<>(dragAndDropMapper.toEntities(dto.getDragAndDrops()));
            dragAndDrops.forEach(dragAndDrop -> {
                dragAndDrop.setQuestion(question);
                question.addDragAndDrop(dragAndDrop);
            });
        }

        return question;
    }

    @Override
    public ArrayList<QuestionDTO> toDTOs(Collection<Question> questions) {
        return questions.stream().map(this::toDTO).collect(toCollection(ArrayList<QuestionDTO>::new));
    }

    @Override
    public ArrayList<Question> toEntities(Collection<QuestionDTO> questionDTOS) {
        return questionDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Question>::new));
    }

    @Override
    public PageResult<QuestionDTO> toDataPage(PageResult<Question> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
