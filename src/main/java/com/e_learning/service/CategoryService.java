package com.e_learning.service;

import com.e_learning.dao.CategoryDao;
import com.e_learning.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseServiceImp<Category>{
    @Autowired
    private CategoryDao categoryDao ;
    @Override
    public JpaRepository<Category, Long> Repository() {
        return categoryDao;
    }


}
