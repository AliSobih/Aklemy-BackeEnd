package com.e_learning.entities.mapper;

import com.e_learning.dto.RoleDTO;
import com.e_learning.entities.Role;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class RoleMapper implements Mapper<Role, RoleDTO> {
    @Override
    public RoleDTO toDTO(Role entity) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(entity.getId());
        roleDTO.setName(entity.getName());
        //    TODO: for arabic
        roleDTO.setNameAr(entity.getNameAr());
        return roleDTO;
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
//        TODO: for arabic
        role.setNameAr(dto.getNameAr());
        return role;
    }

    @Override
    public ArrayList<RoleDTO> toDTOs(Collection<Role> roles) {
        return roles.stream().map(this::toDTO).collect(toCollection(ArrayList<RoleDTO>::new));
    }

    @Override
    public ArrayList<Role> toEntities(Collection<RoleDTO> roleDTOS) {
        return roleDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Role>::new));    }

    @Override
    public PageResult<RoleDTO> toDataPage(PageResult<Role> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
