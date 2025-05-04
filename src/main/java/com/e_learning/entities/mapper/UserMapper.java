package com.e_learning.entities.mapper;

import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.UserDTO;
import com.e_learning.entities.User;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO toDTO(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setName(entity.getName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPassword(entity.getPassword());
        userDTO.setRole(entity.getRole().getName());
        userDTO.setProfilePicture(entity.getProfilePicture());
        userDTO.setEnabled(entity.getEnabled());
        userDTO.setDateJoined(entity.getDateJoined());


        Set<CourseDTO> courses = entity.getCourses().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setTitle(course.getTitle());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setLanguage(course.getLanguage());
            return courseDTO;
        }).collect(Collectors.toSet());

        userDTO.setCourses(courses);

//        TODO: for arabic
        userDTO.setNameAr(entity.getNameAr());
        return userDTO;
    }

    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setProfilePicture(dto.getProfilePicture());

        //        TODO: for arabic
        user.setNameAr(dto.getNameAr());
        return user;
    }

    @Override
    public ArrayList<UserDTO> toDTOs(Collection<User> users) {
        return users.stream().map(this::toDTO).collect(toCollection(ArrayList<UserDTO>::new));
    }

    @Override
    public ArrayList<User> toEntities(Collection<UserDTO> userDTOS) {
        return userDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<User>::new));
    }

    @Override
    public PageResult<UserDTO> toDataPage(PageResult<User> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
