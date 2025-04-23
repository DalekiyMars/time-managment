package com.time.managment.controller;

import com.time.managment.exceptions.SomethingWentWrong;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SomethingWentWrong.class)
    public String handleSomethingWentWrong(SomethingWentWrong ex, Model model) {
        if (Objects.nonNull(model)) {
            model.addAttribute("error", ex.getMessage());
        }
        return "error";
    }
}
