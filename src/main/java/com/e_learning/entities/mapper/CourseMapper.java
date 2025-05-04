package com.e_learning.entities.mapper;

import com.e_learning.dao.*;
import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.DescriptionMasterDTO;
import com.e_learning.dto.LessonDTO;
import com.e_learning.dto.SectionDTO;
import com.e_learning.entities.*;
import com.e_learning.util.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseMapper implements Mapper<Course, CourseDTO> {

    @Autowired
    private final CategoryDao categoryDao;
    @Autowired
    private final CourseDao courseDao;
    @Autowired
    private final UserDao userDao;
    @Autowired
    private final EnrollmentDao enrollmentDao;
    @Autowired
    private final ReviewDao reviewDao;
    @Autowired
    private final LessonMapper lessonMapper;
    @Autowired
    private final SectionMapper sectionMapper;
    @Autowired
    private DescriptionMasterMapper descriptionMasterMapper;
    @Autowired
    private LessonDao lessonDao ;


    @Override
    public CourseDTO toDTO(Course entity) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(entity.getId());
        courseDTO.setTitle(entity.getTitle());
        courseDTO.setDescription(entity.getDescription());
        courseDTO.setLanguage(entity.getLanguage());
        courseDTO.setPrice(entity.getPrice());
        courseDTO.setFixedPrice(entity.getFixedPrice());
        courseDTO.setUpdatedDate(getDate(entity.getUpdate_time()));
        courseDTO.setCategoryId(entity.getCategory().getId());
        courseDTO.setUserId(entity.getInstructor().getId());
        courseDTO.setInstructorNameEn(entity.getInstructor().getName());
        courseDTO.setInstructorNameAr(entity.getInstructor().getNameAr());
        courseDTO.setImageURL(entity.getImageURL());
        courseDTO.setApproved(entity.getApproved());

        Set<SectionDTO> sectionDTOs = entity.getSections().stream()
                .sorted(Comparator.comparing(Section::getPosition))
                .map(section -> {
                    SectionDTO sectionDTO = sectionMapper.toDTO(section);
                    Set<LessonDTO> lessonDTOs = section.getLessons().stream()
                            .sorted(Comparator.comparing(Lesson::getPosition))
                            .map(lessonMapper::toDTO)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                    sectionDTO.setLessons(lessonDTOs);
                    return sectionDTO;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        courseDTO.setSections(sectionDTOs);
        Set<DescriptionMasterDTO> descriptionMasterDTOS = entity.getDescriptionMasters().stream()
                .map(descriptionMasterMapper::toDTO).collect(Collectors.toSet());
        courseDTO.setDescriptionMasterDTOS(descriptionMasterDTOS);

//      for arabic
        courseDTO.setTitleAr(entity.getTitleAr());
        courseDTO.setDescriptionAr(entity.getDescriptionAr());

        int enrollments = enrollmentDao.countEnrollmentsByCourseId(entity.getInstructor().getId());
        courseDTO.setEnrollmentsNum(enrollments);

        int reviews = reviewDao.countReviewsByCourseId(entity.getId());
        courseDTO.setReviewsNum(reviews);

        Double averageRating = reviewDao.getAverageRatingByCourseId(entity.getId());
        if(averageRating==null){
            averageRating = 5.0 ;
        }
        courseDTO.setAverageRating(averageRating);

        Integer totalHours =0 ;
        try {
            totalHours = lessonDao.sumDurationsByCourseId(entity.getId());
            String hoursSummation = (totalHours / 60) + " Hours " + (totalHours % 60) + " Minutes";
            courseDTO.setTotalHours(hoursSummation);
        }catch(Exception ex){
            courseDTO.setTotalHours("0 Hours 0 Minutes");
        }
        Long pdfCount = entity.getSections().stream()
                .flatMap(section -> section.getLessons().stream())
                .filter(lesson -> "pdf".equalsIgnoreCase(lesson.getContentType()))
                .count();
        courseDTO.setPdfCount(pdfCount);

        List<Review> courseReviews = reviewDao.findByCourseId(entity.getId());
        List<Integer> reviewsCounts = getReviewCountsByRating(courseReviews);
        log.debug("reviewsCounts: " + reviewsCounts);
        courseDTO.setRatingCounts(reviewsCounts);

        return courseDTO;
    }

    @Override
    public Course toEntity(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setTitle(dto.getTitle());
        course.setId(dto.getId());
        course.setDescription(dto.getDescription());
        course.setLanguage(dto.getLanguage());
        course.setPrice(dto.getPrice());
        course.setFixedPrice(dto.getFixedPrice());
        //       for full add
        course.setImageURL(dto.getImageURL());
        Category category = categoryDao.getById(dto.getCategoryId());
        category.addCourse(course);
        course.setCategory(category);
        course.setApproved(dto.getApproved());
        User user = userDao.getById(dto.getUserId());
        if  (!courseDao.existsById(dto.getId())) {
            user.addCourse(course);
        }
        course.setInstructor(user);
        Set<Section> sections = new HashSet<>();
        Set<SectionDTO> sectionsDto = dto.getSections();

        if (sectionsDto != null) {
            sectionsDto.forEach(sectionDTO -> {
                Section section = sectionMapper.toEntity(sectionDTO);
                Set<LessonDTO> lessonDTOS = sectionDTO.getLessons();
                if (lessonDTOS != null) {
                    Set<Lesson> lessons = new HashSet<>(lessonMapper.toEntities(lessonDTOS));
                    lessons.forEach(lesson -> lesson.setSection(section));
                    section.setLessons(lessons);
                }
                section.setCourse(course);
                 sections.add(section);
        });
            course.setSections(sections);
        }
        course.setDescription(dto.getDescription());
        course.setTitle(dto.getTitle());
        course.setDescriptionAr(dto.getDescriptionAr());
        course.setTitleAr(dto.getTitleAr());
        Set<DescriptionMasterDTO> descriptionMasterDTOS = dto.getDescriptionMasterDTOS();
        if (descriptionMasterDTOS != null) {
            Set<DescriptionMaster> descriptionMasters = new HashSet<>(descriptionMasterMapper.toEntities(descriptionMasterDTOS));
            descriptionMasters.forEach(descriptionMaster -> descriptionMaster.setCourse(course));
            course.setDescriptionMasters(descriptionMasters);
        }
        return course;
    }


    @Override
    public ArrayList<CourseDTO> toDTOs(Collection<Course> courses) {
        return courses.stream().map(this::toDTO).collect(toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Course> toEntities(Collection<CourseDTO> courseDTOS) {
        return courseDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList::new));
    }

    @Override
    public PageResult<CourseDTO> toDataPage(PageResult<Course> entities) {
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(toCollection(ArrayList<CourseDTO>::new)),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }

    /**
     * this method is used to convert LocalDate to Date
     * @param localDate
     * @return
     */
    public Date getDate(LocalDate localDate) {
        Date date = null;
        try {
            // Convert LocalDate to LocalDateTime at the start of the day
            LocalDateTime localDateTime = localDate.atStartOfDay();

            // Specify the time zone
            ZoneId zoneId = ZoneId.systemDefault(); // Or any specific time zone, e.g., ZoneId.of("UTC")

            // Convert LocalDateTime to ZonedDateTime
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

            // Convert ZonedDateTime to Instant
            Instant instant = zonedDateTime.toInstant();

            // Convert Instant to Date
            date = Date.from(instant);
        } catch (Exception e) {
            //TODO: Log the error using our logger !
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param reviews
     * @return
     */
    public List<Integer> getReviewCountsByRating(List<Review> reviews) {
        // Initialize a list with 0 values for ratings 1 to 5
        List<Integer> reviewCounts = IntStream.range(0, 5)
                .mapToObj(i -> 0)
                .collect(Collectors.toList());

        // Group by rating and count the occurrences
        Map<Integer, Long> ratingCounts = reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

        // Fill the list based on the count for each rating (from 1 to 5)
        ratingCounts.forEach((rating, count) -> reviewCounts.set(rating - 1, count.intValue()));

        return reviewCounts;
    }
}
