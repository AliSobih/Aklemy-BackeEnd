package com.e_learning.controller;

import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.CourseDetailsDTO;
import com.e_learning.dto.requestDTO.SearchRequestDTO;
import com.e_learning.entities.Course;
import com.e_learning.service.CourseService;
import com.e_learning.service.StorageServiceOffline;
import com.e_learning.util.Constants;
import com.e_learning.util.MessageResponse;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin
@Slf4j
public class CourseController extends BaseController<Course, CourseDTO> {
    @Autowired
    private CourseService courseService;
    @Autowired
    private StorageServiceOffline storageServiceOffline;

    @GetMapping("/details/{id}")
    public CourseDetailsDTO getDetails(@PathVariable(value = "id") Long id) {
        return courseService.getDetails(id);
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> uploadCourseImage(@RequestParam("files") List<MultipartFile> files) {
        this.storageServiceOffline.uploadFile(files, Constants.COURSE_IMAGE);
        return new ResponseEntity<>(new MessageResponse("Images has been Updated"), HttpStatus.OK);
    }

    @GetMapping(value = "/downloadImage/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadImgae(@PathVariable String fileName) {
        try {
            if (!fileName.equals("null")) {
                byte[] data = storageServiceOffline.downloadFile(fileName, Constants.COURSE_IMAGE);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return null;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/downloadVideo/{fileName}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String fileName,
                                                           @RequestHeader(value = "Range", required = false) String rangeHeader)  {
        try{
            return storageServiceOffline.getVideoStream(fileName, rangeHeader, Constants.COURSE_VIDEO);
        }catch (Exception ex){
            log.info("video download error "+ex.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/downloadPdf/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable String fileName) {
        try {
            if (!fileName.equals("null")) {
                byte[] data = storageServiceOffline.downloadFile(fileName, Constants.COURSE_VIDEO);
                ByteArrayResource resource = new ByteArrayResource(data);
                return ResponseEntity
                        .ok()
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return null;
            }
        } catch (Exception ex) {
            log.info("download pdf"+ex.getMessage());
            return null;
        }
    }
    @PostMapping("/category/{categoryId}")
    public PageResult<CourseDTO> getCoursesByCategoryId(@PathVariable Long categoryId, @RequestBody PageQueryUtil pageUtil) {
        return courseService.getCoursesByCategoryId(categoryId, pageUtil);
    }

    @PostMapping("/category/{categoryId}/{countryCode}")
    public PageResult<CourseDTO> getCoursesByCategoryIdAndCountryCode(@PathVariable Long categoryId,
                                                                      @PathVariable(value = "countryCode") String countryCod, @RequestBody PageQueryUtil pageUtil) {
        return courseService.getCoursesByCategoryIdAndCountryCode(categoryId, countryCod, pageUtil);
    }

    @PostMapping("/uploadVideo")
    public ResponseEntity<MessageResponse> uploadVideo(@RequestBody() MultipartFile file) {
        try {
            this.storageServiceOffline.uploadFile(List.of(file), Constants.COURSE_VIDEO);
            return new ResponseEntity<>(new MessageResponse("Video has been Uploaded"), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new MessageResponse("An Error Has been occurred "+ ex.getMessage()+"  "), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/{countryCode}", method = RequestMethod.GET)
    public CourseDTO getById(@PathVariable(value = "id") Long id, @PathVariable(value = "countryCode") String countryCode) {
        return courseService.getById(id, countryCode);
    }

    @RequestMapping(value = "/datapage/{countryCode}", method = RequestMethod.POST)
    public PageResult<CourseDTO> getDataPage(@PathVariable(value = "countryCode") String countryCode, @RequestBody PageQueryUtil pageUtil) {
        return courseService.getCoursesPage(countryCode, pageUtil);
    }

    @RequestMapping(value = "/search/{pageNumber}/{size}/{countryCode}", method = RequestMethod.POST)
    public ResponseEntity<PageResult<CourseDTO>> searchCourses(@PathVariable int pageNumber,
                                                               @PathVariable int size,
                                                               @PathVariable(value = "countryCode") String countryCode,
                                                               @RequestBody SearchRequestDTO searchRequestDTO) {
        PageQueryUtil pageUtil = new PageQueryUtil(pageNumber, size);
        return new ResponseEntity<>(courseService.searchCourses(pageUtil, searchRequestDTO, countryCode), HttpStatus.OK);
    }

    @RequestMapping(value = "/approvedPage/{countryCode}", method = RequestMethod.POST)
    public PageResult<CourseDTO> approvedCoursesPage(@PathVariable(value = "countryCode") String countryCode,
                                                     @RequestBody PageQueryUtil pageUtil) {
        return courseService.approvedCoursesPage(countryCode, pageUtil);
    }

    @RequestMapping(value = "/approve/{courseId}", method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse> approveCourse(@PathVariable(value = "courseId") Long courseId) {
        courseService.approveCourse(courseId);
        return new ResponseEntity<>(new MessageResponse("Course has been Approved"), HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/{pageNumber}/{size}", method = RequestMethod.POST)
    public ResponseEntity<PageResult<CourseDTO>> filterCoursesForTeacher(@PathVariable int pageNumber,
                                                            @PathVariable int size,
                                                            @RequestBody SearchRequestDTO searchRequestDTO) {
        PageQueryUtil pageUtil = new PageQueryUtil(pageNumber, size);
        return new ResponseEntity<>(courseService.filterCoursesForTeacher(pageUtil, searchRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("softDelete/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long id) {
        boolean isDeleted = courseService.softDeleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
