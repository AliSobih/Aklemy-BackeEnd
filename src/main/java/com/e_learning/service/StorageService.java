package com.e_learning.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.e_learning.entities.Slider;
import com.e_learning.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StorageService {

    @Value("${buketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private SliderService sliderService;

    public String typeOfBuket(int selection){
        String buket = "" ;
        if(selection==Constants.ADS_SEL){
            buket= bucketName+"/AdsImages";
        }else if(selection==Constants.COURSE_VIDEO){
            buket= bucketName+"/CourseVideo";
        }
        return buket;
    }
    public String uploadFile(List<MultipartFile> files, int selection) {
        List<Slider> sliders=new ArrayList<>();
        for(MultipartFile file :files) {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName =  file.getOriginalFilename();
            String nameOfBucket = typeOfBuket(selection);
            if(selection==Constants.ADS_SEL){
                Slider slider = new Slider();
                slider.setImageUrl(fileName);
                sliders.add(slider);
                log.info(slider.getImageUrl());
            }
            s3Client.putObject(new PutObjectRequest(nameOfBucket, fileName, fileObj));
            fileObj.delete();
        }
        if(selection==Constants.ADS_SEL) {
            this.sliderService.saveAll(sliders);
        }
        return "File uploaded successfully";
    }


    public byte[] downloadFile(String fileName ,int selection) {
        String nameOfBucket= typeOfBuket(selection);
        S3Object s3Object = s3Client.getObject(nameOfBucket, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(List<String> filesName ,int selection) {
        String nameOfBucket= typeOfBuket(selection);
        for(String fileName:filesName) {
            s3Client.deleteObject(nameOfBucket, fileName);
        }
        return" removed successfully";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
    public String uploadFileAd(List<MultipartFile> files, long id, String link) {
        Slider slider = new Slider();
        for(MultipartFile file :files) {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName =  file.getOriginalFilename();
            String nameOfBucket = typeOfBuket(Constants.ADS_SEL);
                slider.setId(id);
                slider.setImageUrl(fileName);
                slider.setLink(link);
            s3Client.putObject(new PutObjectRequest(nameOfBucket, fileName, fileObj));
            fileObj.delete();
            break;
        }
            this.sliderService.save(slider);
        return "File uploaded successfully";
    }

    // method to make stream when i get Video from s3
    public ResponseEntity<InputStreamResource> streamVideo(String fileName, int selection, HttpHeaders headers) {
        String nameOfBucket = typeOfBuket(selection);
        GetObjectRequest getObjectRequest = new GetObjectRequest(nameOfBucket, fileName);
        S3Object s3Object = s3Client.getObject(getObjectRequest);
        long contentLength = s3Object.getObjectMetadata().getContentLength();
        List<HttpRange> ranges = headers.getRange();

        if (ranges.isEmpty()) {
            InputStream inputStream = s3Object.getObjectContent();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, s3Object.getObjectMetadata().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .body(new InputStreamResource(inputStream));
        } else {
            HttpRange range = ranges.get(0);
            long start = range.getRangeStart(contentLength);
            long end = range.getRangeEnd(contentLength);
            long rangeLength = end - start + 1;

            GetObjectRequest rangeRequest = new GetObjectRequest(nameOfBucket, fileName)
                    .withRange(start, end);
            S3Object rangeS3Object = s3Client.getObject(rangeRequest);
            InputStream rangeInputStream = rangeS3Object.getObjectContent();

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, s3Object.getObjectMetadata().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeLength))
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + contentLength)
                    .body(new InputStreamResource(rangeInputStream));
        }
    }
}
