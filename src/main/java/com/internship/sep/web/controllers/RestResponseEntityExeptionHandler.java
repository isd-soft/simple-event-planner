package com.internship.sep.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExeptionHandler {

    @ControllerAdvice
    public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

        }
    }

