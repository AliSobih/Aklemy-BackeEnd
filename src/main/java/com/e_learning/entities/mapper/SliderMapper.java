package com.e_learning.entities.mapper;

import com.e_learning.dto.SliderDTO;
import com.e_learning.entities.Slider;
import com.e_learning.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
public class SliderMapper implements Mapper<Slider, SliderDTO> {

    @Override
    public SliderDTO toDTO(Slider entity) {
        SliderDTO sliderDTO =new SliderDTO();
        sliderDTO.setId(entity.getId());
        sliderDTO.setImageUrl(entity.getImageUrl());
        sliderDTO.setLink(entity.getLink());
        return sliderDTO;
    }

    @Override
    public Slider toEntity(SliderDTO dto) {
        Slider slider =new Slider();
        slider.setId(dto.getId());
        slider.setImageUrl(dto.getImageUrl());
        slider.setLink(dto.getLink());
        return slider;
    }

    @Override
    public ArrayList<SliderDTO> toDTOs(Collection<Slider> sliders) {
        return sliders.stream().map(entity -> toDTO(entity)).collect(toCollection(ArrayList<SliderDTO>::new));
    }

    @Override
    public ArrayList<Slider> toEntities(Collection<SliderDTO> sliderDTOS) {
        return sliderDTOS.stream().map(dto -> toEntity(dto)).collect(toCollection(ArrayList<Slider>::new));
    }

    @Override
    public PageResult<SliderDTO> toDataPage(PageResult<Slider> entities) {
        return null;
    }
}
