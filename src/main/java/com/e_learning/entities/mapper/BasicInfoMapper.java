package com.e_learning.entities.mapper;

import com.e_learning.dto.BasicInfoDTO;
import com.e_learning.entities.BasicInfo;
import com.e_learning.util.PageResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toCollection;

@Component
@AllArgsConstructor
public class BasicInfoMapper implements Mapper<BasicInfo, BasicInfoDTO> {

    @Override
    public BasicInfoDTO toDTO(BasicInfo entity) {
        BasicInfoDTO basicInfoDTO = new BasicInfoDTO();
        basicInfoDTO.setInstagram(entity.getInstagram());
        basicInfoDTO.setFacebook(entity.getFacebook());
        basicInfoDTO.setPhone(entity.getPhone());
        basicInfoDTO.setTwitter(entity.getTwitter());
        basicInfoDTO.setWhatsApp(entity.getWhatsApp());
        basicInfoDTO.setId(entity.getId());
        basicInfoDTO.setTiktok(entity.getTiktok());
        basicInfoDTO.setMessenger(entity.getMessenger());
        basicInfoDTO.setTelegram(entity.getTelegram());
        basicInfoDTO.setYoutube(entity.getYoutube());
        return basicInfoDTO;
    }

    @Override
    public BasicInfo toEntity(BasicInfoDTO dto) {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setInstagram(dto.getInstagram());
        basicInfo.setFacebook(dto.getFacebook());
        basicInfo.setPhone(dto.getPhone());
        basicInfo.setTwitter(dto.getTwitter());
        basicInfo.setWhatsApp(dto.getWhatsApp());
        basicInfo.setId(dto.getId());
        basicInfo.setTiktok(dto.getTiktok());
        basicInfo.setMessenger(dto.getMessenger());
        basicInfo.setTelegram(dto.getTelegram());
        basicInfo.setYoutube(dto.getYoutube());
        return basicInfo;
    }

    @Override
    public ArrayList<BasicInfoDTO> toDTOs(Collection<BasicInfo> basicInfos) {
        return basicInfos.stream().map(entity -> toDTO(entity)).collect(toCollection(ArrayList<BasicInfoDTO>::new));
    }



    @Override
    public ArrayList<BasicInfo> toEntities(Collection<BasicInfoDTO> basicInfoDTOS) {
        return basicInfoDTOS.stream().map(dto -> toEntity(dto)).collect(toCollection(ArrayList<BasicInfo>::new));
    }

    @Override
    public PageResult<BasicInfoDTO> toDataPage(PageResult<BasicInfo> entities) {

        return new PageResult<>(entities.getData().stream().map(entity ->
                toDTO(entity)).collect(toCollection(ArrayList<BasicInfoDTO>::new))
                , entities.getTotalCount(), entities.getPageSize(), entities.getCurrPage());
    }
}
