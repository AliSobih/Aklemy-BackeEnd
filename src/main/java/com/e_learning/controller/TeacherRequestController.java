package com.e_learning.controller;

import com.e_learning.dto.TeacherRequestDTO;
import com.e_learning.entities.TeacherRequest;
import com.e_learning.service.TeacherRequestService;
import com.e_learning.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teachers_requests")
public class TeacherRequestController extends BaseController<TeacherRequest, TeacherRequestDTO> {

    @Autowired
    private TeacherRequestService teacherRequestService;

    @PatchMapping("/approve/{userEmail}")
    public ResponseEntity<MessageResponse> approveTeacherRequest(@PathVariable(value = "userEmail") String userEmail) throws Exception {
        teacherRequestService.approve(userEmail);
        return new ResponseEntity<>(new MessageResponse("Teacher Request has been Approved"), HttpStatus.OK);
    }

    @PostMapping(value = "/add-teachers-request")
    public MessageResponse add(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) throws Exception {
        teacherRequestService.createTeacherRequest(teacherRequestDTO);
        return new MessageResponse("Teacher Request has been saved successfully");
    }
}
