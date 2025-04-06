package com.time.managment.controller;

import com.time.managment.constants.ExceptionCodes;
import com.time.managment.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleValidationErrors(NoSuchElementException ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_EXCEPTION, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
