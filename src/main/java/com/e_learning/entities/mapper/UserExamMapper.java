package com.e_learning.entities.mapper;

import com.e_learning.dao.*;
import com.e_learning.dto.UserExamDTO;
import com.e_learning.dto.UserExamQuestionDTO;
import com.e_learning.entities.*;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserExamMapper implements Mapper<UserExam, UserExamDTO> {
    @Autowired
    private UserExamQuestionsMapper userExamQuestionsMapper;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private UserExamAnswerDao userExamAnswerDao;
    @Autowired
    private UserExamQuestionDao userExamQuestionDao;

    @Override
    public UserExamDTO toDTO(UserExam entity) {
        if (entity == null) {
            return null;
        }

        UserExamDTO dto = new UserExamDTO();
        dto.setId(entity.getId());
        dto.setExamId(entity.getExam().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setCorrectAnswers(entity.getCorrectAnswers());
        dto.setWrongAnswers(entity.getWrongAnswers());
        dto.setRemainingTime(entity.getRemainingTime());
        dto.setNetTime(entity.getNetTime());
        dto.setLanguage(entity.getLanguage());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getExam().getTitle());
        dto.setTitleAr(entity.getExam().getTitleAr());
        dto.setDescription(entity.getExam().getDescription());
        dto.setDescriptionAr(entity.getExam().getDescriptionAr());

        Set<UserExamQuestionDTO> userExamQuestionDTOS = entity.getExamQuestions().stream()
                .sorted(Comparator.comparing(UserExamQuestions::getId))
                .map(userExamQuestionsMapper::toDTO)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

        dto.setUserExamQuestions(userExamQuestionDTOS);
        return dto;
    }

    @Override
    public UserExam toEntity(UserExamDTO dto) {
        Optional<Exam> exam = examDao.findById(dto.getExamId());
        Optional<User> user = userDao.findById(dto.getUserId());
        UserExam userExam = new UserExam();
        userExam.setId(dto.getId());
        if (exam.isPresent() && user.isPresent()) {

            // Prepare UserExam object
            userExam.setExam(exam.get());
            userExam.setUser(user.get());
            userExam.setQuestionNumber(exam.get().getQuestionsNumber());
            userExam.setRemainingTime(exam.get().getTime());
            userExam.setLanguage(dto.getLanguage());

            // Prepare Questions and Answers
            List<Question> questions = questionDao.findByExamId(exam.get().getId(), exam.get().getQuestionsNumber());
            List<UserExamQuestions> userExamQuestions = new ArrayList<>();

            questions.forEach(question -> {
                UserExamQuestions userExamQuestion = new UserExamQuestions();
                userExamQuestion.setUserExam(userExam);
                userExamQuestion.setQuestion(question);
                userExamQuestion.setStatus(question.getStatus());
                userExamQuestion.setImagePath(question.getImagePath());

//                if (question.getAnswers() != null && !userExamQuestion.getAnswers().isEmpty()) {
                    List<UserExamAnswers> answersForQuestion = createUserExamAnswers(userExamQuestion, new ArrayList<>(question.getAnswers()));
                    userExamQuestion.setAnswers(new HashSet<>(answersForQuestion));
//                }

//                if (userExamQuestion.getDragAndDrops() != null && !userExamQuestion.getDragAndDrops().isEmpty()) {
                    List<UserExamDragAndDrop> dragAndDropsForQuestion = createUserExamDragAndDrop(userExamQuestion, new ArrayList<>(question.getDragAndDrops()));
                    userExamQuestion.setDragAndDrops(new HashSet<>(dragAndDropsForQuestion));
//                }
                userExamQuestions.add(userExamQuestion);
            });
            userExam.setExamQuestions(new HashSet<>(userExamQuestions));
        }

        return userExam;
    }

    @Override
    public ArrayList<UserExamDTO> toDTOs(Collection<UserExam> userExams) {
        return userExams.stream().map(this::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<UserExam> toEntities(Collection<UserExamDTO> userExamDTOS) {
        return userExamDTOS.stream().map(this::toEntity).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public PageResult<UserExamDTO> toDataPage(PageResult<UserExam> entities) {
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

    private List<UserExamAnswers> createUserExamAnswers(UserExamQuestions userExamQuestion, List<Answer> answers) {
        return answers.stream().map(answer -> {
            UserExamAnswers userExamAnswer = new UserExamAnswers();
            userExamAnswer.setUserExamQuestions(userExamQuestion);
            userExamAnswer.setAnswer(answer.getAnswer());
            userExamAnswer.setAnswerAr(answer.getAnswerAr());
            userExamAnswer.setIsCorrect(answer.getIsCorrect());
            return userExamAnswer;
        }).collect(Collectors.toList());
    }

    private List<UserExamDragAndDrop> createUserExamDragAndDrop(UserExamQuestions userExamQuestion, List<DragAndDrop> dragAndDrops) {
        return dragAndDrops.stream().map(dragAndDrop -> {
            UserExamDragAndDrop userExamDragAndDrop = new UserExamDragAndDrop();
            userExamDragAndDrop.setUserExamQuestion(userExamQuestion);
            userExamDragAndDrop.setDragItem(dragAndDrop.getDragItem());
            userExamDragAndDrop.setDropItem(dragAndDrop.getDropItem());
            userExamDragAndDrop.setRandomDropItem(dragAndDrop.getRandomDropItem());
            userExamDragAndDrop.setDragItemAr(dragAndDrop.getDragItemAr());
            userExamDragAndDrop.setDropItemAr(dragAndDrop.getDropItemAr());
            userExamDragAndDrop.setRandomDropItemAr(dragAndDrop.getRandomDropItemAr());
            return userExamDragAndDrop;
        }).collect(Collectors.toList());
    }
}
