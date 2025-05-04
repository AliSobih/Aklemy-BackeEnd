package com.e_learning.service;

import com.e_learning.dao.RoleDao;
import com.e_learning.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseServiceImp<Role> {
    @Autowired
    private RoleDao roleDao;
    @Override
    public JpaRepository<Role, Long> Repository() {
        return roleDao;
    }
}
