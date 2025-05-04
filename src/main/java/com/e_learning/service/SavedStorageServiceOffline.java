package com.e_learning.service;

import com.e_learning.entities.Slider;
import com.e_learning.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SavedStorageServiceOffline {

    private String storageDirectory = "src\\main\\resources\\Images\\";

    @Autowired
    private SliderService sliderService;

    public String typeOfDirectory(int selection){
        String directory = storageDirectory;
        if (selection == Constants.ADS_SEL) {
            directory = storageDirectory + "Ads\\" ;
        }else if (selection==Constants.COURSE_IMAGE){
            directory = storageDirectory + "course\\" ;
        }else if(selection==Constants.COURSE_VIDEO){
            directory = storageDirectory + "courseVideo\\" ;
        }else if(selection==Constants.CATEGORY_IMAGE){
            directory = storageDirectory + "category\\" ;
        }else if(selection==Constants.QUESTION_IMAGE){
            directory = storageDirectory + "question\\" ;
        }
        return directory;
    }

    public String uploadFile(List<MultipartFile> files, int selection) {
        List<Slider> sliders = new ArrayList<>();
        String directoryPath = typeOfDirectory(selection);
        for (MultipartFile file : files) {
            File fileObj = convertMultiPartFileToFile(file, directoryPath);
            String fileName = file.getOriginalFilename();
            String filePath = directoryPath + "\\" + fileName;  // Save to folder
            if (selection == Constants.ADS_SEL) {
                Slider slider = new Slider();
                slider.setImageUrl(fileName);
                sliders.add(slider);
            }
        }

        if (selection == Constants.ADS_SEL) {
            sliderService.saveAll(sliders);
        }
        return "File uploaded successfully";
    }

    public byte[] downloadFile(String fileName, int selection) {
        String directoryPath = typeOfDirectory(selection);
        Path filePath = Paths.get(directoryPath, fileName);
        try {
            return Files.readAllBytes(filePath);
        } catch (Exception e) {
            log.error("Error reading file from local storage", e);
        }
        return null;
    }

    public String deleteFile(List<String> fileNames, int selection) {
        String directoryPath = typeOfDirectory(selection);

        for (String fileName : fileNames) {
            Path filePath = Paths.get(directoryPath, fileName);
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                log.error("Error deleting file from local storage", e);
            }
        }
        return "Files removed successfully";
    }

    private File convertMultiPartFileToFile(MultipartFile file, String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File convertedFile = new File(directoryPath + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipart file to file", e);
        }
        return convertedFile;
    }

    public String uploadFileAd(List<MultipartFile> files, long id, String link) {
        Slider slider = new Slider();
        String directoryPath = typeOfDirectory(Constants.ADS_SEL);

        for (MultipartFile file : files) {
            File fileObj = convertMultiPartFileToFile(file, directoryPath);
            String fileName = file.getOriginalFilename();
            String filePath = directoryPath + "/" + fileName;  // Save to folder

            slider.setId(id);
            slider.setImageUrl(filePath);
            slider.setLink(link);
            break;
        }

        sliderService.save(slider);
        return "File uploaded successfully";
    }

}
