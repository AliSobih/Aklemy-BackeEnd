package com.e_learning.entities.mapper;

import com.e_learning.dto.CategoryDTO;
import com.e_learning.entities.Category;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;
@Component
public class CategoryMapper implements Mapper<Category, CategoryDTO>{

    @Override
    public CategoryDTO toDTO(Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(entity.getId());
        categoryDTO.setName(entity.getName());
        categoryDTO.setDescription(entity.getDescription());

        //    TODO: for arabic
        categoryDTO.setNameAr(entity.getNameAr());
        categoryDTO.setDescriptionAr(entity.getDescriptionAr());
        categoryDTO.setImageUrl(entity.getImageUrl());
        return categoryDTO;
    }

    @Override
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        //    TODO: for arabic
        category.setNameAr(dto.getNameAr());
        category.setDescriptionAr(dto.getDescriptionAr());
        category.setImageUrl(dto.getImageUrl());
        return category;

    }

    @Override
    public ArrayList<CategoryDTO> toDTOs(Collection<Category> categories) {
       return categories.stream().map(this::toDTO).collect(toCollection(ArrayList<CategoryDTO>::new));
    }

    @Override
    public ArrayList<Category> toEntities(Collection<CategoryDTO> categoryDTOS) {
        return categoryDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Category>::new));
    }

    @Override
    public PageResult<CategoryDTO> toDataPage(PageResult<Category> entities) {
        return new PageResult<>(toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
