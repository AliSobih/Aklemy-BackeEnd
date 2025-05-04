package com.e_learning.controller;

import com.e_learning.dto.CourseDTO;
import com.e_learning.dto.EnrollmentDTO;
import com.e_learning.dto.requestDTO.EnrollmentSearchRequestDTO;
import com.e_learning.entities.Enrollment;
import com.e_learning.entities.mapper.EnrollmentMapper;
import com.e_learning.service.EnrollmentService;
import com.e_learning.util.MessageResponse;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin
public class EnrollmentController extends BaseController<Enrollment, EnrollmentDTO> {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public MessageResponse create(@Valid @RequestBody EnrollmentDTO dto) {
        enrollmentService.save(enrollmentMapper.toEntity(dto));
        return new MessageResponse("Item has been saved successfully");
    }

    @RequestMapping(value = "/search/{pageNumber}/{size}", method = RequestMethod.POST)
    public ResponseEntity<PageResult<EnrollmentDTO>> search(@PathVariable int pageNumber,
                                                            @PathVariable int size,
                                                            @RequestBody EnrollmentSearchRequestDTO enrollmentRequestDTO) {
        PageQueryUtil pageUtil = new PageQueryUtil(pageNumber, size);
        return new ResponseEntity<>(enrollmentService.search(pageUtil, enrollmentRequestDTO), HttpStatus.OK);
    }
    @PostMapping(value = "/user/{userId}")
    public PageResult<CourseDTO> getEnrollmentByUserId(@PathVariable Long userId, @RequestBody PageQueryUtil pageUtil){
        return enrollmentService.getEnrollmentByUserId(userId,pageUtil);
    }

    @GetMapping("/course/{courseId}/{userId}")
    public ResponseEntity<CourseDTO> getEnrollmentByUserId(@PathVariable Long courseId, @PathVariable Long userId) {
        return new ResponseEntity<>(enrollmentService.getEnrolmentByCourseIdAndUserId(courseId, userId), HttpStatus.OK);
    }

    @PatchMapping("/approve/{enrollId}")
    public void approve(@PathVariable Long enrollId) {
        enrollmentService.togelApproval(enrollId);
    }

}
