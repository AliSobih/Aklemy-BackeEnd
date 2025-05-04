package com.e_learning.service;

import com.e_learning.dao.RoleDao;
import com.e_learning.dao.TeacherRequestDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.TeacherRequestDTO;
import com.e_learning.entities.Role;
import com.e_learning.entities.TeacherRequest;
import com.e_learning.entities.User;
import com.e_learning.entities.mapper.TeacherRequestMapper;
import com.e_learning.security.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherRequestService extends BaseServiceImp<TeacherRequest> {
    @Autowired
    private TeacherRequestDao teacherRequestDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private TeacherRequestMapper teacherRequestMapper;

    @Override
    public JpaRepository<TeacherRequest, Long> Repository() {
        return teacherRequestDao;
    }

    /**
     * @param userEmail
     */
    public void approve(String userEmail) throws Exception {
        Optional<TeacherRequest> teacherRequest = teacherRequestDao.findByEmail(userEmail);
        if (teacherRequest.isPresent()) {
            Optional<User> user = this.userDao.findByEmail(userEmail);
            if (user.isPresent()) {
                Role role = roleDao.findByName("ROLE_TEACHER");
                user.get().setRole(role);

                userDao.save(user.get());

                teacherRequestDao.delete(teacherRequest.get());
            } else {
                throw new UserNotFoundException(userEmail);
            }
        } else {
            throw new Exception("Teacher Request with email: " + userEmail + " doesn't exists");
        }

    }

    /**
     * @param teacherRequestDTO
     * @throws Exception
     */
    public void createTeacherRequest(TeacherRequestDTO teacherRequestDTO) throws Exception {
        String userEmail = teacherRequestDTO.getEmail();
        Optional<TeacherRequest> optionalTeacherRequest = teacherRequestDao.findByEmail(userEmail);
        if (optionalTeacherRequest.isEmpty()) {
            Optional<User> user = this.userDao.findByEmail(userEmail);
            if (user.isPresent()) {
                String userRole = user.get().getRole().getName();
                if (userRole.equalsIgnoreCase("ROLE_STUDENT")) {
                    TeacherRequest teacherRequest = teacherRequestMapper.toEntity(teacherRequestDTO);
                    teacherRequestDao.save(teacherRequest);
                }
            } else {
                throw new UserNotFoundException(userEmail);
            }
        } else {
            throw new Exception("Teacher Request with email: " + userEmail + " already exists");
        }
    }
}
