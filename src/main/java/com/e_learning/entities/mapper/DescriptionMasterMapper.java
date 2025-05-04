package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dto.DescriptionDetailDTO;
import com.e_learning.dto.DescriptionMasterDTO;
import com.e_learning.entities.Course;
import com.e_learning.entities.DescriptionDetail;
import com.e_learning.entities.DescriptionMaster;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Component
public class DescriptionMasterMapper implements Mapper<DescriptionMaster, DescriptionMasterDTO> {
    @Autowired
    private DescriptionDetailMapper detailMapper;

    @Autowired
    private CourseDao courseDao;

    @Override
    public DescriptionMasterDTO toDTO(DescriptionMaster entity) {
        DescriptionMasterDTO dto = new DescriptionMasterDTO();
        dto.setId(entity.getId());
        dto.setNote(entity.getNote());
        Set<DescriptionDetailDTO> detailDTOS = entity.getDetails().stream()
                .map(descriptionDetail -> detailMapper.toDTO(descriptionDetail)).collect(Collectors.toSet());
        dto.setDetails(detailDTOS);
        dto.setCourseId(entity.getCourse().getId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setNoteAr(entity.getNoteAr());
        return dto;
    }

    @Override
    public DescriptionMaster toEntity(DescriptionMasterDTO dto) {
        DescriptionMaster descriptionMaster = new DescriptionMaster();
        descriptionMaster.setId(dto.getId());
        descriptionMaster.setNote(dto.getNote());
        descriptionMaster.setId(dto.getId());
        if (dto.getDetails() != null) {
            Set<DescriptionDetail> descriptionDetails =
                    dto.getDetails().stream().map(descriptionDetailDTO -> {
                                DescriptionDetail detail = detailMapper.toEntity(descriptionDetailDTO);
                                detail.setDescriptionMaster(descriptionMaster);
                                return detail;
                            })
                            .collect(Collectors.toSet());
            descriptionMaster.setDetails(descriptionDetails);
        }

        if (dto.getCourseId() != null) {
            Course course = courseDao.getById(dto.getCourseId());
            descriptionMaster.setCourse(course);
            course.addDescriptionMaster(descriptionMaster);
        }

//        TODO: for arabic
        descriptionMaster.setNoteAr(dto.getNoteAr());
        return descriptionMaster;
    }

    @Override
    public ArrayList<DescriptionMasterDTO> toDTOs(Collection<DescriptionMaster> descriptionMasters) {
        return descriptionMasters.stream().map(this::toDTO).collect(toCollection(ArrayList<DescriptionMasterDTO>::new));
    }

    @Override
    public ArrayList<DescriptionMaster> toEntities(Collection<DescriptionMasterDTO> requirementDTOS) {
        return requirementDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<DescriptionMaster>::new));
    }

    @Override
    public PageResult<DescriptionMasterDTO> toDataPage(PageResult<DescriptionMaster> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
