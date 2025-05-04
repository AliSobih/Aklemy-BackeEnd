package com.e_learning.service;

import com.e_learning.dao.WatchedLessonDao;
import com.e_learning.dto.WatchedLessonDTO;
import com.e_learning.dto.WatchedLessonsPerSection;
import com.e_learning.entities.Course;
import com.e_learning.entities.User;
import com.e_learning.entities.WatchedLesson;
import com.e_learning.entities.mapper.WatchedLessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WatchedLessonService extends BaseServiceImp<WatchedLesson> {
    @Autowired
    private WatchedLessonDao watchedLessonDao;
    @Autowired
    private WatchedLessonMapper watchedLessonMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @Override
    public JpaRepository<WatchedLesson, Long> Repository() {
        return watchedLessonDao;
    }

    public void save(WatchedLessonDTO watchedLessonDTO) {
        boolean alreadyExists = watchedLessonDao.existsByUserIdAndCourseIdAndSectionIdAndLessonId(
                watchedLessonDTO.getUserId(),
                watchedLessonDTO.getCourseId(),
                watchedLessonDTO.getSectionId(),
                watchedLessonDTO.getLessonId());

        if (!alreadyExists) {
            WatchedLesson watchedLesson = watchedLessonMapper.toEntity(watchedLessonDTO);
            watchedLessonDao.save(watchedLesson);
        }
    }

    /**
     * @param courseId
     * @param userId
     * @return
     */
    public List<WatchedLessonsPerSection> getWatchedLessonsPerSections(Long courseId, Long userId) {
        User user = userService.findById(userId);
        Course course = courseService.findById(courseId);
        List<WatchedLessonsPerSection> watchedLessonsPerSections = new ArrayList<>(); // Initialize the list

        if (user != null && course != null) {
            Optional.ofNullable(course.getSections()).ifPresent(sections -> {
                sections.forEach(section -> {
                    Long sectionId = section.getId();
                    WatchedLessonsPerSection watchedLessonsPerSection = new WatchedLessonsPerSection(); // Create the object
                    watchedLessonsPerSection.setSectionId(sectionId);
                    watchedLessonsPerSection.setWatchedLessons(watchedLessonDao.countByUserIdAndSectionId(user.getId(), sectionId));

                    watchedLessonsPerSections.add(watchedLessonsPerSection); // Add the object to the list
                });
            });
        }

        return watchedLessonsPerSections; // Return the list
    }
}
