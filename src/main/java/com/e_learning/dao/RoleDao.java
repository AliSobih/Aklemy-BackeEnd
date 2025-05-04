package com.e_learning.dao;

import com.e_learning.entities.Role;

public interface RoleDao extends BaseDao<Role>{
    boolean existsByName(String name);
    Role findByName(String name);
}
