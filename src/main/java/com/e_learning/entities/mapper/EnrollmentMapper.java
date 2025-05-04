package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.EnrollmentDTO;
import com.e_learning.dto.UserDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.User;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.toCollection;

@Component
public class EnrollmentMapper implements Mapper<Enrollment, EnrollmentDTO> {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao studentDao;
    @Override
    public EnrollmentDTO toDTO(Enrollment entity) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(entity.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(entity.getEnrollmentDate());
        enrollmentDTO.setEnrollmentDate(formattedDate);
        enrollmentDTO.setApprove(entity.isApprove());
        // add course to JSON
        CourseDTO courseDTO = new CourseDTO();
        Course course = entity.getCourse();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setTitleAr(course.getTitleAr());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setDescriptionAr(course.getDescriptionAr());
        courseDTO.setLanguage(course.getLanguage());
        courseDTO.setPrice(course.getPrice());
        enrollmentDTO.setCourse(courseDTO);

        // add student to JSON
        User student = entity.getStudent();
        UserDTO studentDto = new UserDTO();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setNameAr(student.getNameAr());
        studentDto.setEmail(student.getEmail());
        studentDto.setProfilePicture(student.getProfilePicture());
        enrollmentDTO.setStudent(studentDto);
        enrollmentDTO.setStudentNameAr(student.getNameAr());
        enrollmentDTO.setStudentName(student.getName());
        enrollmentDTO.setStudentId(studentDto.getId());

        return enrollmentDTO;
    }

    @Override
    public Enrollment toEntity(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setEnrollmentDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        Course course = courseDao.getById(dto.getCourse().getId());
        enrollment.setCourse(course);

        User student = studentDao.getById(dto.getStudentId());
        enrollment.setStudent(student);
        return enrollment;
    }

    @Override
    public ArrayList<EnrollmentDTO> toDTOs(Collection<Enrollment> enrollments) {
        return enrollments.stream().map(this::toDTO).collect(toCollection(ArrayList<EnrollmentDTO>::new));
    }

    @Override
    public ArrayList<Enrollment> toEntities(Collection<EnrollmentDTO> enrollmentDTOS) {
        return enrollmentDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Enrollment>::new));
    }

    @Override
    public PageResult<EnrollmentDTO> toDataPage(PageResult<Enrollment> entities) {
        return new PageResult<>(
                entities.getData().stream().map(this::toDTO).collect(toCollection(ArrayList<EnrollmentDTO>::new)),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
