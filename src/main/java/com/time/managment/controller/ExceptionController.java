package com.time.managment.controller;

import com.time.managment.dto.DepartmentCreateRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("departmentForm", new DepartmentCreateRequest());
        return "departments";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Непредвиденная ошибка: " + ex.getMessage());
        model.addAttribute("departmentForm", new DepartmentCreateRequest());
        return "departments";
    }
}
