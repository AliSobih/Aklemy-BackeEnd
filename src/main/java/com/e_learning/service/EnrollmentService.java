package com.e_learning.service;

import com.e_learning.dao.EnrollmentDao;
import com.e_learning.dao.WatchedLessonDao;
import com.e_learning.dao.specification.EnrollmentSpecification;
import com.e_learning.dto.BaseDTO;
import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.EnrollmentDTO;
import com.e_learning.dto.requestDTO.EnrollmentSearchRequestDTO;
import com.e_learning.dto.requestDTO.SearchRequestDTO;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.mapper.CourseMapper;
import com.e_learning.entities.mapper.EnrollmentMapper;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentService extends BaseServiceImp<Enrollment> {
    @Autowired
    private EnrollmentDao enrollmentDao;
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private WatchedLessonDao watchedLessonDao;

    @Override
    public JpaRepository<Enrollment, Long> Repository() {
        return enrollmentDao;
    }

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    /**
     * @param pageUtil
     * @param enrollmentRequestDTO
     * @return PageResult
     */
    public PageResult<EnrollmentDTO> search(PageQueryUtil pageUtil, EnrollmentSearchRequestDTO enrollmentRequestDTO) {
        Page<Enrollment> EnrollmentPage;

        String searchValue = enrollmentRequestDTO.getSearchValue();
        Date enrollmentDateFrom = enrollmentRequestDTO.getEnrollmentDateFrom();
        Date enrollmentDateTo = enrollmentRequestDTO.getEnrollmentDateTo();
        String loggedInUserEmail = enrollmentRequestDTO.getLoggedInUserEmail();
        Boolean isAdminUser = enrollmentRequestDTO.getIsAdminUser();

        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit(), constructSortObject(enrollmentRequestDTO));

        if ((searchValue != null && !searchValue.trim().isEmpty()) ||
                enrollmentDateFrom != null || enrollmentDateTo != null ||
                (loggedInUserEmail != null && !loggedInUserEmail.trim().isEmpty()) || isAdminUser != null) {
            EnrollmentSpecification EnrollmentSpecification =
                    new EnrollmentSpecification(searchValue, enrollmentDateFrom, enrollmentDateTo, loggedInUserEmail, isAdminUser);

            EnrollmentPage = enrollmentDao.findAll(EnrollmentSpecification, pageable);
        } else {
            EnrollmentPage = enrollmentDao.findAll(pageable);
        }
        PageResult<Enrollment> pageResult = new PageResult<>(EnrollmentPage.getContent(), (int) EnrollmentPage.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage());

        return enrollmentMapper.toDataPage(pageResult);
    }

    private Sort constructSortObject(SearchRequestDTO requestDTO) {
        if (requestDTO.getSortDirection() == null) {
            return Sort.by(Sort.Direction.DESC, "enrollmentDate");
        }
        return Sort.by(Sort.Direction.valueOf(requestDTO.getSortDirection()), requestDTO.getSortBy());
    }

    public PageResult<CourseDTO> getEnrollmentByUserId(Long userId, PageQueryUtil pageUtil) {
        Pageable pageable = PageRequest.of(pageUtil.getPage() - 1, pageUtil.getLimit());
        Page<Enrollment> enrollments = enrollmentDao.findByStudentIdAndApproveTrue(userId, pageable);
        List<CourseDTO> courseDTOs = enrollments.stream()
                .map(enrollment -> courseMapper.toDTO(enrollment.getCourse()))
                .collect(Collectors.toList());

        setWatchedLessonsForCourses(courseDTOs, userId);

        PageResult<CourseDTO> pageResult = new PageResult<>(courseDTOs, (int) enrollments.getTotalElements(),
                pageUtil.getLimit(), pageUtil.getPage()-1);
        return pageResult;
    }

    public CourseDTO getEnrolmentByCourseIdAndUserId(Long courseId, Long userId ) {
        Enrollment enrollment = enrollmentDao.findByStudentIdAndCourseId(userId, courseId);
        CourseDTO courseDTO = courseMapper.toDTO(enrollment.getCourse());
        setWatchedLessonsForCourses(new ArrayList<>(Arrays.asList(courseDTO)), userId);
        return courseDTO;
    }

    /**
     * Marks lessons as watched for the given user and list of courseDTOs.
     *
     * @param courseDtos List of CourseDTO objects.
     * @param userId     ID of the user.
     */
    private void setWatchedLessonsForCourses(List<CourseDTO> courseDtos, Long userId) {
        List<Long> coursesIds = courseDtos.stream().map(BaseDTO::getId).collect(Collectors.toList());
        Set<Long> watchedLessonsIds = watchedLessonDao.findWatchedLessonIdsByUserAndCourses(userId, coursesIds);

        // Iterate through courses
        courseDtos.forEach(courseDTO -> {
            // Check if sections are null or empty
            Optional.ofNullable(courseDTO.getSections()).ifPresent(sections -> {
                sections.forEach(sectionDTO -> {
                    // Check if lessons are null or empty
                    Optional.ofNullable(sectionDTO.getLessons()).ifPresent(lessons -> {
                        lessons.forEach(lessonDTO -> {
                            // Mark lesson as watched if it exists in the watchedLessonIds set
                            lessonDTO.setWatched(watchedLessonsIds.contains(lessonDTO.getId()));
                        });
                    });
                });
            });
        });

    }

    public void togelApproval(Long id){
        Optional<Enrollment> enrollment = enrollmentDao.findById(id);
        if (enrollment.isPresent()) {
            enrollment.get().setApprove(!enrollment.get().isApprove());
            enrollmentDao.save(enrollment.get());
        }
    }

    @Override
    public Enrollment save(Enrollment entity) {
        Enrollment enrollment = enrollmentDao.findByStudentIdAndCourseId(entity.getStudent().getId(), entity.getCourse().getId());
        if (enrollment == null) {
            return super.save(entity);
        }
        return null;
    }
}
