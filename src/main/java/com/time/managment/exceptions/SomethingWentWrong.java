package com.time.managment.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.ui.Model;

@Getter
@Setter
@Accessors(chain = true)
public class SomethingWentWrong extends RuntimeException{
    private String modelName;
    private Model model;
    public SomethingWentWrong(String message) {
        super(message);
    }
}
