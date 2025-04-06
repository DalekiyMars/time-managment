package com.time.managment.constants;

import lombok.Getter;

@Getter
public enum ExceptionCodes {
    HANDLE_SOMETHING_WRONG("ERR-001", Constants.ExceptionMessages.SOMETHING_WENT_WRONG),
    HANDLE_EXCEPTION("ERR-002", Constants.ExceptionMessages.ELEM_NOT_FOUND),
    HANDLE_FIELD("ERR-003", Constants.ExceptionDescriptions.DATA_INTEGRITY_VIOLATION, Constants.ExceptionMessages.FIELD_EXCEPTION);

    private final String code;
    private final String description;
    private final String message;

    ExceptionCodes(String code, String description) {
        this.code = code;
        this.description = description;
        this.message = null;
    }

    ExceptionCodes(String code, String description, String message) {
        this.code = code;
        this.description = description;
        this.message = message;
    }

}
