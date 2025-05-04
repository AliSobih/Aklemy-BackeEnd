package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.EnrollmentDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.CertificateDTO;
import com.e_learning.entities.*;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;

@Component
public class CertificateMapper implements Mapper<Certificate, CertificateDTO> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private EnrollmentDao enrollmentDao;


    @Override
    public CertificateDTO toDTO(Certificate entity) {
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setId(entity.getId());
        certificateDTO.setIssuedDate(entity.getIssuedDate());
        certificateDTO.setCertificateURL(entity.getCertificateURL());
        certificateDTO.setEnrollmentId(entity.getEnrollment().getId());

        Course course = entity.getEnrollment().getCourse();
        certificateDTO.setCourseId(course.getId());
        certificateDTO.setCourseName(course.getTitle());
        certificateDTO.setCourseNameAr(course.getTitleAr());
        certificateDTO.setInstructorName(course.getInstructor().getName());
        certificateDTO.setInstructorNameAr(course.getInstructor().getNameAr());

        int totalHours = course.getSections().stream()
                .flatMap(section -> section.getLessons().stream())
                .mapToInt(Lesson::getDuration)
                .sum();
        certificateDTO.setTotalHours(totalHours);

        User student = entity.getEnrollment().getStudent();
        certificateDTO.setStudentId(student.getId());
        certificateDTO.setStudentName(student.getName());
        certificateDTO.setStudentNameAr(student.getNameAr());

        return certificateDTO;
    }

    @Override
    public Certificate toEntity(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setIssuedDate(new Date());
        certificate.setCertificateURL(dto.getCertificateURL());

        Optional<User> student = userDao.findById(dto.getStudentId());
        Optional<Course> course = courseDao.findById(dto.getCourseId());
        if (student.isPresent() && course.isPresent()) {
            Enrollment enrollment = enrollmentDao
                    .findByStudentIdAndCourseId(student.get().getId(), course.get().getId());
            certificate.setEnrollment(enrollment);
        }

        return certificate;
    }

    @Override
    public ArrayList<CertificateDTO> toDTOs(Collection<Certificate> certificates) {
        return certificates.stream().map(this::toDTO).collect(toCollection(ArrayList<CertificateDTO>::new));
    }

    @Override
    public ArrayList<Certificate> toEntities(Collection<CertificateDTO> certificateDTOS) {
        return certificateDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Certificate>::new));
    }

    @Override
    public PageResult<CertificateDTO> toDataPage(PageResult<Certificate> entities) {
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(toCollection(ArrayList<CertificateDTO>::new)),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
