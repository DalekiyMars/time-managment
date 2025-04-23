package com.time.managment.controller;

import com.time.managment.exceptions.SomethingWentWrong;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SomethingWentWrong.class)
    public String handleSomethingWentWrong(SomethingWentWrong ex, Model model) {
        if (Objects.nonNull(model)) {
            model.addAttribute("error", ex.getMessage());
        }
        return "access-denied";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("error", "У вас нет прав для доступа к этому ресурсу.");
        return "access-denied";
    }
}
