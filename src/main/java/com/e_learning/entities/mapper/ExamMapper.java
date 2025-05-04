package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.QuestionDao;
import com.e_learning.dao.SectionDao;
import com.e_learning.dto.ExamDTO;
import com.e_learning.dto.QuestionDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.Exam;
import com.e_learning.entities.Question;
import com.e_learning.entities.Section;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Slf4j
@Component
public class ExamMapper implements Mapper<Exam, ExamDTO> {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDao questionDao;

    public ExamDTO toDTO(Exam entity) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(entity.getId());
        examDTO.setCourseId(entity.getCourse().getId());
        if (entity.getSection() != null) {
            examDTO.setSectionId(entity.getSection().getId());
        }
        Set<QuestionDTO> questionDTOS = new HashSet<>(questionMapper.toDTOs(entity.getQuestions()));
        examDTO.setQuestions(questionDTOS);
        examDTO.setId(entity.getId());
        examDTO.setDescription(entity.getDescription());
        examDTO.setDescriptionAr(entity.getDescriptionAr());
        examDTO.setTitle(entity.getTitle());
        examDTO.setTitleAr(entity.getTitleAr());
        try {
            if (entity.getCourse().getId() != null) {
                examDTO.setCourseId(entity.getCourse().getId());
            }
        } catch (Exception ex) {

        }
        try {
            if (entity.getSection().getId() != null) {
                examDTO.setSectionId(entity.getSection().getId());
            }
        } catch (Exception ex) {
        }

        examDTO.setTime(entity.getTime());
        examDTO.setQuestionsNumber(entity.getQuestionsNumber());
        return examDTO;
    }

    @Override
    public Exam toEntity(ExamDTO dto) {
        if (dto.getCourseId() == null) {
           return null;
        }
        Exam exam = new Exam();
        exam.setId(dto.getId());
        exam.setTitle(dto.getTitle());
        exam.setTitleAr(dto.getTitleAr());
        exam.setDescription(dto.getDescription());
        exam.setDescriptionAr(dto.getDescriptionAr());
        exam.setTime(dto.getTime());
        exam.setQuestionsNumber(dto.getQuestionsNumber());
        Course course = courseDao.getById(dto.getCourseId());
        exam.setCourse(course);
//
        Section section = null;
        if (dto.getSectionId() != null && dto.getSectionId() != 0) {
            section = sectionDao.getById(dto.getSectionId());
            exam.setSection(section);
        }
        Set<Question> questions = new HashSet<>();
        Set<QuestionDTO> questionDTOS = dto.getQuestions();

        if (questionDTOS != null && !questionDTOS.isEmpty()) {
            questions = questionDTOS.stream()
                    .map(questionDTO -> {
                        return questionDao.findById(questionDTO.getId())
                                .orElseThrow(() -> new NoSuchElementException("Question not found"));
                    })
                    .collect(Collectors.toSet());

            exam.setQuestions(questions);
        }

        return exam;
    }

    @Override
    public ArrayList<ExamDTO> toDTOs(Collection<Exam> exams) {
        return exams.stream().map(this::toDTO).collect(toCollection(ArrayList<ExamDTO>::new));
    }

    @Override
    public ArrayList<Exam> toEntities(Collection<ExamDTO> examDTOs) {
        return examDTOs.stream().map(this::toEntity).collect(toCollection(ArrayList<Exam>::new));
    }

    @Override
    public PageResult<ExamDTO> toDataPage(PageResult<Exam> entities) {
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(toCollection(ArrayList<ExamDTO>::new)),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
