package com.e_learning.service;

import com.e_learning.dao.DescriptionDetailDao;
import com.e_learning.entities.DescriptionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DescriptionDetailService extends BaseServiceImp<DescriptionDetail>{
    @Autowired
    private DescriptionDetailDao descriptionDetailDao;
    @Override
    public JpaRepository<DescriptionDetail, Long> Repository() {
        return descriptionDetailDao;
    }
}
