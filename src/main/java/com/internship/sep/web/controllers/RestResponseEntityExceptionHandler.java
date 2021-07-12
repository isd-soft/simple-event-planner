package com.internship.sep.web.controllers;

import com.internship.sep.services.ResourceNotFoundException;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {

        return ResponseEntity.badRequest().body("Resource Not Found");
        //return new ResponseEntity<>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({InvalidCredentialsException.class})
//    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
//        return ResponseEntity.badRequest().body("Invalid credentials");
//    }
//
//    @ExceptionHandler({DataIntegrityViolationException.class})
//    public ResponseEntity<?> handleDataIntegrityViolationException() {
//        return ResponseEntity.badRequest().body("Email already registered");
//    }
}


