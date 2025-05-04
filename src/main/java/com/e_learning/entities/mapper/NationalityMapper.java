package com.e_learning.entities.mapper;

import com.e_learning.dao.CourseDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.NationalityDTO;
import com.e_learning.entities.Nationality;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;
@Slf4j
@Component
public class NationalityMapper implements Mapper<Nationality, NationalityDTO> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Override
    public NationalityDTO toDTO(Nationality entity) {
        NationalityDTO dto = new NationalityDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFactor(entity.getFactor());
        dto.setCurrency(entity.getCurrency());
        dto.setNameAr(entity.getNameAr());
        dto.setRateExchange(entity.getRateExchange());
        dto.setCurrencyAr(entity.getCurrencyAr());
        dto.setCourseId(entity.getCourse().getId());
        dto.setCountryCode(entity.getCountryCode());
        return dto;
    }

    @Override
    public Nationality toEntity(NationalityDTO dto) {
        Nationality entity = new Nationality();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNameAr(dto.getNameAr());
        entity.setFactor(dto.getFactor());
        entity.setCurrency(dto.getCurrency());
        entity.setCurrencyAr(dto.getCurrencyAr());
        entity.setRateExchange(dto.getRateExchange());
        entity.setCourse(courseDao.findById(dto.getCourseId()).get());
        entity.setCountryCode(dto.getCountryCode());
        return entity;
    }

    @Override
    public ArrayList<NationalityDTO> toDTOs(Collection<Nationality> nationalities) {
        return nationalities.stream().map(this::toDTO).collect(toCollection(ArrayList<NationalityDTO>::new));
    }

    @Override
    public ArrayList<Nationality> toEntities(Collection<NationalityDTO> nationalityDTOS) {
        return nationalityDTOS.stream().map(this::toEntity).collect(toCollection(ArrayList<Nationality>::new));
    }

    @Override
    public PageResult<NationalityDTO> toDataPage(PageResult<Nationality> entities) {
        return new PageResult<>(
                toDTOs(entities.getData()),
                entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage()
        );
    }
}
