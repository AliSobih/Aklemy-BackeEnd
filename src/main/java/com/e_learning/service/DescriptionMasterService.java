package com.e_learning.service;

import com.e_learning.dao.DescriptionMasterDao;
import com.e_learning.dto.DescriptionMasterDTO;
import com.e_learning.entities.DescriptionMaster;
import com.e_learning.entities.mapper.DescriptionMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptionMasterService extends BaseServiceImp<DescriptionMaster>{
    @Autowired
    private DescriptionMasterDao descriptionMasterDao;


    @Autowired
    private DescriptionMasterMapper descriptionMasterMapper ;

    @Override
    public JpaRepository<DescriptionMaster, Long> Repository() {
        return descriptionMasterDao;
    }

    public List<DescriptionMasterDTO> getDescriptionByCourseId(Long courseId){
//        return null;
        return descriptionMasterMapper.toDTOs(descriptionMasterDao.findByCourseId(courseId));
    }
}
