package com.e_learning.service;

import com.e_learning.dao.LessonDao;
import com.e_learning.dao.WatchedLessonDao;
import com.e_learning.dto.LessonDTO;
import com.e_learning.entities.Lesson;
import com.e_learning.entities.WatchedLesson;
import com.e_learning.entities.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService extends BaseServiceImp<Lesson> {
    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private WatchedLessonDao watchedLessonDao;

    @Override
    public JpaRepository<Lesson, Long> Repository() {
        return lessonDao;
    }

    @Transactional
    public LessonDTO updateLesson(Long id, LessonDTO lessonDTO) {
        Optional<Lesson> optionalUser = lessonDao.findById(id);
        if (optionalUser.isPresent()) {
            Lesson lesson = optionalUser.get();
            lesson.setContentType(lessonDTO.getContentType());
            System.out.println(lessonDTO.getDuration() / 60);
            lesson.setDuration(Math.round(lessonDTO.getDuration() / 60));
            lesson.setContentURL(lessonDTO.getContentURL());
            lessonDao.save(lesson);
            return lessonMapper.toDTO(lesson);
        } else {
            throw new RuntimeException("Lesson not found with id " + id);
        }
    }

    /**
     *
     * @param userId
     * @param sectionId
     * @return
     */
    public List<LessonDTO> getWatchedLessons(Long userId, Long sectionId) {
        Set<WatchedLesson> watchedLessons = watchedLessonDao.findByUserIdAndSectionId(userId, sectionId);
        List<LessonDTO> lessonDTOS = List.of();
        if (watchedLessons != null && !watchedLessons.isEmpty()) {
            List<Long> watchedLessonsIds = watchedLessons.stream().map(watchedLesson -> watchedLesson.getLesson().getId())
                    .collect(Collectors.toList());
            List<Lesson> lessons = lessonDao.findAllById(watchedLessonsIds);
            lessonDTOS = lessonMapper.toDTOs(lessons);
            lessonDTOS.forEach(lessonDTO -> {
                lessonDTO.setWatched(true);
            });
        }
        return lessonDTOS;
    }
}
