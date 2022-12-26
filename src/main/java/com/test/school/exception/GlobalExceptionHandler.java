package com.test.school.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){

        ModelAndView errorPage = new ModelAndView("error.html");
        errorPage.addObject("errorMessage", exception.getMessage());
        exception.printStackTrace();
        return errorPage;
    }
}