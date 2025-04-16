package com.time.managment.controller;

import com.time.managment.exceptions.SomethingWentWrong;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SomethingWentWrong.class)
    public String handleIllegalArgument(SomethingWentWrong ex) {
        ex.getModel().addAttribute("error", "Имя пользователя уже занято. Пожалуйста, выберите другое.");
        return ex.getModelName();
    }
}
