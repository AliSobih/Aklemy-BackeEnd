package com.e_learning.entities.mapper;

import com.e_learning.dto.TeacherRequestDTO;
import com.e_learning.entities.TeacherRequest;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class TeacherRequestMapper implements Mapper<TeacherRequest, TeacherRequestDTO> {

    @Override
    public TeacherRequestDTO toDTO(TeacherRequest entity) {
        TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
        teacherRequestDTO.setId(entity.getId());
        teacherRequestDTO.setName(entity.getName());
        teacherRequestDTO.setEmail(entity.getEmail());
        teacherRequestDTO.setPhone(entity.getPhone());
        teacherRequestDTO.setCity(entity.getCity());
        teacherRequestDTO.setCoursesName(entity.getCoursesName());
        teacherRequestDTO.setJobTitle(entity.getJobTitle());
        teacherRequestDTO.setTrainerNumber(entity.getTrainerNumber());
        teacherRequestDTO.setNotes(entity.getNotes());
        return teacherRequestDTO;
    }

    @Override
    public TeacherRequest toEntity(TeacherRequestDTO dto) {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setId(dto.getId());
        teacherRequest.setName(dto.getName());
        teacherRequest.setEmail(dto.getEmail());
        teacherRequest.setPhone(dto.getPhone());
        teacherRequest.setCity(dto.getCity());
        teacherRequest.setCoursesName(dto.getCoursesName());
        teacherRequest.setJobTitle(dto.getJobTitle());
        teacherRequest.setTrainerNumber(dto.getTrainerNumber());
        teacherRequest.setNotes(dto.getNotes());
        return teacherRequest;
    }

    @Override
    public ArrayList<TeacherRequestDTO> toDTOs(Collection<TeacherRequest> teacherRequests) {
        return teacherRequests.stream().map(this::toDTO).collect(toCollection(ArrayList<TeacherRequestDTO>::new));
    }

    @Override
    public ArrayList<TeacherRequest> toEntities(Collection<TeacherRequestDTO> teacherRequestDTOS) {
        return teacherRequestDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<TeacherRequest>::new));
    }

    @Override
    public PageResult<TeacherRequestDTO> toDataPage(PageResult<TeacherRequest> entities) {
        return new PageResult<>(toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
