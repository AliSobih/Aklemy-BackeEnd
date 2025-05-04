package com.e_learning.controller;

import com.e_learning.dto.CategoryDTO;
import com.e_learning.entities.Category;
import com.e_learning.service.StorageServiceOffline;
import com.e_learning.util.Constants;
import com.e_learning.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
@Slf4j
public class CategoryController extends BaseController<Category, CategoryDTO>{

    @Autowired
    private StorageServiceOffline storageServiceOffline;

    @PostMapping(value = "/uploadImage")
    public ResponseEntity<MessageResponse> uploadCourseImage(@RequestParam("file") MultipartFile file) {
        this.storageServiceOffline.uploadFile(List.of(file), Constants.CATEGORY_IMAGE);
        return new ResponseEntity<>(new MessageResponse("Images has been uploaded"), HttpStatus.OK);
    }
    @GetMapping(value = "/downloadImage/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadImgae(@PathVariable String fileName) {
        try {
            if (!fileName.equals("null")) {
                byte[] data = storageServiceOffline.downloadFile(fileName, Constants.CATEGORY_IMAGE);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            }else {
                return null ;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return null;
        }
    }
}
