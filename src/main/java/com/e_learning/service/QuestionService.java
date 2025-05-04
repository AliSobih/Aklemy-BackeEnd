package com.e_learning.service;

import com.e_learning.dao.AnswerDao;
import com.e_learning.dao.DragAndDropDao;
import com.e_learning.dao.QuestionDao;
import com.e_learning.dao.specification.QuestionSpecification;
import com.e_learning.dto.AnswerDTO;
import com.e_learning.dto.DragAndDropDTO;
import com.e_learning.dto.QuestionCorrectAnswersDTO;
import com.e_learning.dto.QuestionDTO;
import com.e_learning.dto.requestDTO.QuestionSearchRequestDTO;
import com.e_learning.entities.Answer;
import com.e_learning.entities.DragAndDrop;
import com.e_learning.entities.Question;
import com.e_learning.entities.mapper.AnswerMapper;
import com.e_learning.entities.mapper.DragAndDropMapper;
import com.e_learning.entities.mapper.QuestionMapper;
import com.e_learning.util.Constants;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService extends BaseServiceImp<Question> {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private DragAndDropDao dragAndDropDao;
    @Autowired
    private DragAndDropMapper dragAndDropMapper;

    @Override
    public JpaRepository<Question, Long> Repository() {
        return questionDao;
    }

    public PageResult<QuestionDTO> searchQuestions(PageQueryUtil pageUtil, QuestionSearchRequestDTO questionRequestDTO) {
        Page<Question> questionPage;
        String subject = questionRequestDTO.getSubject();
        Long courseId = questionRequestDTO.getCourseId();
        String level = questionRequestDTO.getLevel();
        String chapter = questionRequestDTO.getChapter();
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());

        if ((subject != null && !subject.trim().isEmpty()) || courseId != null ||
            (level != null && !level.trim().isEmpty()) || (chapter != null && !chapter.trim().isEmpty())) {
            QuestionSpecification questionSpecification = new QuestionSpecification(subject, courseId, level, chapter);
            questionPage = questionDao.findAll(questionSpecification, pageable);
        } else {
            questionPage = questionDao.findAll(pageable);
        }

        PageResult<Question> pageResult = new PageResult<>(questionPage.getContent(), (int) questionPage.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        return questionMapper.toDataPage(pageResult);
    }

    public PageResult<QuestionDTO> getQuestionsByCourseId(Long courseId, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Question> page = questionDao.findAllByCourseId(courseId, pageable);
        PageResult<Question> pageResult = new PageResult<>(page.getContent(), (int) page.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());
        return questionMapper.toDataPage(pageResult);
    }


    public QuestionCorrectAnswersDTO getCorrectAnswersById(Long questionId) {
        QuestionCorrectAnswersDTO questionCorrectAnswersDTO = new QuestionCorrectAnswersDTO();
        Optional<Question> question = questionDao.findById(questionId);
        if (question.isPresent()) {
            if (Constants.QUESTION_STATUS_DRAG_AND_DROP.equals(question.get().getStatus())) {
                List<DragAndDrop> dragAndDrops = dragAndDropDao.findAllByQuestionId(questionId);
                List<DragAndDropDTO> dragAndDropDTOS = dragAndDropMapper.toDTOs(dragAndDrops);
                questionCorrectAnswersDTO.setDragAndDropDTOS(dragAndDropDTOS);
            } else {
                List<Answer> answers = answerDao.findAllByQuestionId(questionId);
                List<AnswerDTO> answerDTOS = answerMapper.toDTOs(answers);
                questionCorrectAnswersDTO.setAnswerDTOS(answerDTOS);
            }
        }
        return questionCorrectAnswersDTO;
    }
}
