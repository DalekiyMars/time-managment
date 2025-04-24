package com.time.managment.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SomethingWentWrong extends RuntimeException{
    private String modelName;
    public SomethingWentWrong(String message) {
        super(message);
    }
}
