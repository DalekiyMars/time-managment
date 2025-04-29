package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.exceptions.SomethingWentWrong;
import jakarta.validation.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
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
    public String handleAccessDenied(Model model) {
        model.addAttribute("error", "У вас нет прав для доступа к этому ресурсу.");
        return "access-denied";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Model handleAccessDenied(ConstraintViolationException ex, Model model) {
        model.addAttribute("error", "У вас нет прав для доступа к этому ресурсу.");
        return model;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public Model handleValidationErrors(NoSuchElementException ex, Model model) {
        model.addAttribute(Constants.ModelValues.MESSAGE, ex.getMessage());
        return model;
    }

    @ExceptionHandler(Exception.class)
    public String handleValidationErrors(Exception ex, Model model) {
        model.addAttribute("error", "У вас нет прав для доступа к этому ресурсу.");
        return "access-denied";
    }
}
