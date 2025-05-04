package com.e_learning.entities.mapper;

import com.e_learning.dao.SectionDao;
import com.e_learning.dto.LessonDTO;
import com.e_learning.entities.Lesson;
import com.e_learning.entities.Section;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
@Slf4j
public class LessonMapper implements Mapper<Lesson, LessonDTO> {
    @Autowired
    private SectionDao sectionDao;
    @Override
    public LessonDTO toDTO(Lesson entity) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(entity.getId());
        lessonDTO.setTitle(entity.getTitle());
        lessonDTO.setContentType(entity.getContentType());
        lessonDTO.setContentURL(entity.getContentURL());
        // front end need to return duration in seconds not in minutes
        lessonDTO.setDuration(entity.getDuration()*60);
        lessonDTO.setPosition(entity.getPosition());
        lessonDTO.setSectionId(entity.getSection().getId());
        lessonDTO.setIsVisible(entity.getIsVisible());
        //    TODO: for arabic
        lessonDTO.setTitleAr(entity.getTitleAr());
        lessonDTO.setContentTypeAr(entity.getContentTypeAr());
        return lessonDTO;
    }

    @Override
    public Lesson toEntity(LessonDTO dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setTitle(dto.getTitle());
        lesson.setContentType(dto.getContentType());
        lesson.setContentURL(dto.getContentURL());
        lesson.setDuration(dto.getDuration()/60);
        lesson.setPosition(dto.getPosition());
        lesson.setIsVisible(dto.getIsVisible());

        if (dto.getSectionId() != null && dto.getSectionId()!=0) {
            Section section = sectionDao.getById(dto.getSectionId());
            lesson.setSection(section);
            section.addLesson(lesson);
        }

        //    TODO: for arabic
        lesson.setTitleAr(dto.getTitleAr());
        lesson.setContentTypeAr(dto.getContentTypeAr());
        return lesson;
    }

    @Override
    public ArrayList<LessonDTO> toDTOs(Collection<Lesson> lessons) {
        return lessons.stream().map(this::toDTO).collect(toCollection(ArrayList<LessonDTO>::new));
    }

    @Override
    public ArrayList<Lesson> toEntities(Collection<LessonDTO> lessonDTOS) {
        return lessonDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Lesson>::new));
    }

    @Override
    public PageResult<LessonDTO> toDataPage(PageResult<Lesson> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
