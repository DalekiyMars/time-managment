package com.time.managment.dto;

import com.time.managment.constants.ExceptionCodes;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String error;
    private final String message;

    /**
     * Constructs an {@code ErrorResponse} instance based on the specified error type.
     * The error message is retrieved from the given {@code ErrorType}.
     *
     * @param code the type of error containing the error code, description and default message
     */
    public ErrorResponse(ExceptionCodes code) {
        this.errorCode = code.getCode();
        this.error = code.getDescription();
        this.message = code.getMessage();
    }

    /**
     * Constructs an {@code ErrorResponse} instance with the specified error type and custom message.
     * This constructor is used when a custom message is needed instead of the default one.
     *
     * @param code    the type of error containing the error code
     * @param message the custom error message (use it to override default message or if its null)
     */
    public ErrorResponse(ExceptionCodes code, String message) {
        this.errorCode = code.getCode();
        this.error = code.getDescription();
        this.message = message;
    }

    public ErrorResponse(String errorCode, String error, String message) {
        this.errorCode = errorCode;
        this.error = error;
        this.message = message;
    }
}
