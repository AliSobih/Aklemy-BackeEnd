package com.e_learning.exception;

import com.e_learning.util.MessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;


@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LogManager.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("error here "+ex.getMessage());
        return new ResponseEntity<>(new MessageResponse("An error has occurred please try again later"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.e_learning.exception.InvalidUserNameOrPasswordException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(com.e_learning.exception.InvalidUserNameOrPasswordException ex, WebRequest request) {
        log.error(ex.getMessage());
        MessageResponse messageResponse=new MessageResponse(ex.getMessage());
        messageResponse.setField(   ex.getField());
        return new ResponseEntity<>(messageResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.e_learning.exception.UserNameOrEmailAlreadyExistException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(com.e_learning.exception.UserNameOrEmailAlreadyExistException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.e_learning.exception.UserNotFoundException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(com.e_learning.exception.UserNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.e_learning.exception.ItemNotFoundException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(com.e_learning.exception.ItemNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
       MessageResponse messageResponse = new MessageResponse(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        messageResponse.setField(ex.getFieldError().getField());
        log.error(ex.getFieldError());
        return new ResponseEntity<>(messageResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResponse> globalExceptionHandler(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(Objects.requireNonNull(ex.getMessage())
                );
        messageResponse.setField(null);
        log.error(ex.getMessage());
        return new ResponseEntity<>(messageResponse,
                HttpStatus.BAD_REQUEST);
    }


}
