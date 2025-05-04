package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dto.LessonDTO;
import com.e_learning.dto.SectionDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.Lesson;
import com.e_learning.entities.Section;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

@Component
public class SectionMapper implements Mapper<Section, SectionDTO> {
    @Autowired
    private CourseDao courseDao ;
    @Autowired
    private LessonMapper lessonMapper  ;
    @Override
    public SectionDTO toDTO(Section entity) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setId(entity.getId());
        sectionDTO.setTitle(entity.getTitle());
        sectionDTO.setPosition(entity.getPosition());
        Set<LessonDTO> lessonDTOS = new HashSet<>(lessonMapper.toDTOs(entity.getLessons()));
        sectionDTO.setLessons(lessonDTOS);
        sectionDTO.setCourseID(entity.getCourse().getId());

//        TODO: for arabic
        sectionDTO.setTitleAr(entity.getTitleAr());
        return sectionDTO;
    }

    @Override
    public Section toEntity(SectionDTO dto) {
        Section section = new Section();
        section.setTitle(dto.getTitle());
        section.setPosition(dto.getPosition());
        section.setId(dto.getId());
        if( dto.getCourseID() != null) {
            Course course = courseDao.getById(dto.getCourseID());
            section.setCourse(course);
            course.addSection(section);
        }

        Set<LessonDTO> lessonDTOS = dto.getLessons();

        if (lessonDTOS != null) {
            Set<Lesson> lessons = new HashSet<>(lessonMapper.toEntities(lessonDTOS));
            lessons.forEach(lesson -> {
                lesson.setSection(section);
                section.addLesson(lesson);
            });
        }

//        TODO: for arabic
        section.setTitleAr(dto.getTitleAr());
        return section;
    }

    @Override
    public ArrayList<SectionDTO> toDTOs(Collection<Section> sections) {
        return sections.stream().map(this::toDTO).collect(toCollection(ArrayList<SectionDTO>::new));
    }

    @Override
    public ArrayList<Section> toEntities(Collection<SectionDTO> sectionDTOS) {
        return sectionDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Section>::new));
    }

    @Override
    public PageResult<SectionDTO> toDataPage(PageResult<Section> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
