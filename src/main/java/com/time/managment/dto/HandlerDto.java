package com.time.managment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class HandlerDto {
    private Boolean success;
    private String message;
}