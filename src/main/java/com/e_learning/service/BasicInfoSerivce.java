package com.e_learning.service;

import com.e_learning.dao.BasicInfoDao;
import com.e_learning.entities.BasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BasicInfoSerivce extends BaseServiceImp<BasicInfo>{
    @Autowired
    private BasicInfoDao basicInfoDao ;
    @Override
    public JpaRepository<BasicInfo, Long> Repository() {
        return basicInfoDao;
    }
}
