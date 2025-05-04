package com.e_learning.service;

import com.e_learning.dao.NationalityDao;
import com.e_learning.dto.NationalityDTO;
import com.e_learning.entities.Nationality;
import com.e_learning.entities.mapper.NationalityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalityService extends BaseServiceImp<Nationality> {
    @Autowired
    private NationalityDao nationalityDao;
    @Autowired
    private NationalityMapper nationalityMapper ;
    @Override
    public JpaRepository<Nationality, Long> Repository() {
        return nationalityDao;
    }

    public List<NationalityDTO> getNationalityByCourseId(Long courseId){
        return nationalityMapper.toDTOs(nationalityDao.findBycourseId(courseId));
    }
}
