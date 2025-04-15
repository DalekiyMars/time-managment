package com.time.managment.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ModelAndView handleIllegalArgument(IllegalArgumentException ex) {
//        ModelAndView mav = new ModelAndView("department-add");
//        mav.addObject("message", "Неверные данные: " + ex.getMessage());
//        mav.addObject("success", false);
//        return mav;
//    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleIllegalArgument(DataIntegrityViolationException ex) {
        ModelAndView mav = new ModelAndView("weekend-add");
        mav.addObject("message", "Неверные данные: " + ex.getMessage());
        mav.addObject("success", false);
        return mav;
    }
}
