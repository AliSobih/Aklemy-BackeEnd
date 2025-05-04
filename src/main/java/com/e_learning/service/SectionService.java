package com.e_learning.service;

import com.e_learning.dao.SectionDao;
import com.e_learning.entities.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SectionService extends BaseServiceImp<Section> {
    @Autowired
    private SectionDao sectionDao;
    @Override
    public JpaRepository<Section, Long> Repository() {
        return sectionDao;
    }
}
