package com.e_learning.entities.mapper;

import com.e_learning.dao.DescriptionMasterDao;
import com.e_learning.dto.DescriptionDetailDTO;
import com.e_learning.entities.DescriptionDetail;
import com.e_learning.entities.DescriptionMaster;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class DescriptionDetailMapper implements Mapper<DescriptionDetail, DescriptionDetailDTO> {
    @Autowired
    private DescriptionMasterDao descriptionMasterDao;
    @Override
    public DescriptionDetailDTO toDTO(DescriptionDetail entity) {
        DescriptionDetailDTO dto = new DescriptionDetailDTO();
        dto.setId(entity.getId());
        dto.setNote(entity.getNote());
        dto.setDescriptionId(entity.getDescriptionMaster().getId());
        dto.setNoteAr(entity.getNoteAr());
        return dto;
    }

    @Override
    public DescriptionDetail toEntity(DescriptionDetailDTO dto) {
        DescriptionDetail descriptionDetail = new DescriptionDetail();
        descriptionDetail.setId(dto.getId());
        descriptionDetail.setNote(dto.getNote());
        descriptionDetail.setNoteAr(dto.getNoteAr());
        if (dto.getDescriptionId() != null) {
            DescriptionMaster descriptionMaster = descriptionMasterDao.getById(dto.getDescriptionId());
            descriptionDetail.setDescriptionMaster(descriptionMaster);
            descriptionMaster.addDetail(descriptionDetail);
        }
        return descriptionDetail;
    }

    @Override
    public ArrayList<DescriptionDetailDTO> toDTOs(Collection<DescriptionDetail> descriptionDetails) {
        return descriptionDetails.stream().map(this::toDTO).collect(toCollection(ArrayList<DescriptionDetailDTO>::new));
    }

    @Override
    public ArrayList<DescriptionDetail> toEntities(Collection<DescriptionDetailDTO> outComeDTOS) {
        return outComeDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<DescriptionDetail>::new));
    }

    @Override
    public PageResult<DescriptionDetailDTO> toDataPage(PageResult<DescriptionDetail> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
