package com.e_learning.service;

import com.e_learning.dao.SliderDao;
import com.e_learning.entities.Slider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SliderService extends BaseServiceImp<Slider>   {

    @Autowired
    private SliderDao sliderDao;

    @Override
    public JpaRepository<Slider, Long> Repository() {
        return sliderDao;
    }

    public List<Slider> getAllAds(){
        List<Slider> sliders = this.sliderDao.findAll();
        return sliders;
    }
    @Transactional
    public void deleteImagesByName(String name){
        this.sliderDao.deleteImagesByName(name);
    }


}
