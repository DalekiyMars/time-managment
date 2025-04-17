package com.time.managment.controller;

import com.time.managment.exceptions.SomethingWentWrong;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SomethingWentWrong.class)
    public String handleSomethingWentWrong(SomethingWentWrong ex, Model model) {
        if (model != null) {
            model.addAttribute("error", ex.getMessage());
        }
        return "error";
    }
}
