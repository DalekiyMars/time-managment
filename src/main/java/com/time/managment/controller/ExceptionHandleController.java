package com.time.managment.controller;

import com.time.managment.constants.ExceptionCodes;
import com.time.managment.dto.ErrorResponse;
import com.time.managment.exceptions.SomethingWentWrong;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
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

    @ExceptionHandler(SomethingWentWrong.class)
    public ResponseEntity<?> handleValidationErrors(SomethingWentWrong ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_SOMETHING_WRONG, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationErrors(ConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_SOMETHING_WRONG, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleValidationErrors(IllegalArgumentException ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_FIELD, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleValidationErrors(DataIntegrityViolationException ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_FIELD, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleValidationErrors(DateTimeParseException  ex) {
        ErrorResponse response = new ErrorResponse(ExceptionCodes.HANDLE_FIELD, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
