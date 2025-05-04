package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.LessonDao;
import com.e_learning.dao.SectionDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.WatchedLessonDTO;
import com.e_learning.entities.*;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
@Slf4j
public class WatchedLessonMapper implements Mapper<WatchedLesson, WatchedLessonDTO> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private LessonDao lessonDao;

    @Override
    public WatchedLessonDTO toDTO(WatchedLesson entity) {
        WatchedLessonDTO watchedLessonDTO = new WatchedLessonDTO();

        watchedLessonDTO.setId(entity.getId());
        watchedLessonDTO.setUserId(entity.getUser().getId());
        watchedLessonDTO.setCourseId(entity.getCourse().getId());
        watchedLessonDTO.setSectionId(entity.getSection().getId());
        watchedLessonDTO.setLessonId(entity.getLesson().getId());

        return watchedLessonDTO;
    }

    @Override
    public WatchedLesson toEntity(WatchedLessonDTO dto) {
        WatchedLesson watchedLesson = new WatchedLesson();

        watchedLesson.setId(dto.getId());

        if (dto.getUserId() != null && dto.getUserId() != 0) {
            User user = userDao.getById(dto.getUserId());
            watchedLesson.setUser(user);
        }
        if (dto.getCourseId() != null && dto.getCourseId() != 0) {
            Course course = courseDao.getById(dto.getCourseId());
            watchedLesson.setCourse(course);
        }
        if (dto.getSectionId() != null && dto.getSectionId() != 0) {
            Section section = sectionDao.getById(dto.getSectionId());
            watchedLesson.setSection(section);
        }
        if (dto.getLessonId() != null && dto.getLessonId() != 0) {
            Lesson lesson = lessonDao.getById(dto.getLessonId());
            watchedLesson.setLesson(lesson);
        }

        return watchedLesson;
    }

    @Override
    public ArrayList<WatchedLessonDTO> toDTOs(Collection<WatchedLesson> watchedLessons) {
        return watchedLessons.stream().map(this::toDTO).collect(toCollection(ArrayList<WatchedLessonDTO>::new));
    }

    @Override
    public ArrayList<WatchedLesson> toEntities(Collection<WatchedLessonDTO> watchedLessonDTOS) {
        return watchedLessonDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<WatchedLesson>::new));
    }

    @Override
    public PageResult<WatchedLessonDTO> toDataPage(PageResult<WatchedLesson> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
