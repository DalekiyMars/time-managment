package com.time.managment.constants;

public class Constants {
    public static class ExceptionDescriptions{
        public static final String NO_SUCH_ELEMENT = "Element not found";
        public static final String DATA_INTEGRITY_VIOLATION = "Data Integrity Violation";
    }
    public static class ExceptionMessages{
        public static final String DATE_CONVERTING_EXCEPTION = "Date type exception";
        public static final String SOMETHING_WENT_WRONG = "Something went wrong";
        public static final String ELEM_NOT_FOUND = "Element Not Found";
        public static final String  FIELD_EXCEPTION= "Entity loss necessary field/s";
        public static final String TIMESHEET_ALREADY_EXISTS = "Пользователь с таким табельным номером уже существует";
    }
    public static class ClassicMessages{
        public static final String INFO_DELETED_SUCCESSFULLY = "Информация о выходном удалена.";
    }
    public static class PasswordValues{
        public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER = UPPER.toLowerCase();
        public static final String DIGITS = "0123456789";
        public static final String ALL = UPPER + LOWER + DIGITS;
        public static final int DEFAULT_LENGTH = 9;
    }
    public static class ModelValues{
        public static final String MESSAGE = "message";
        public static final String SUCCESS = "success";
        public static final String WEEKENDS = "weekends";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String DEPARTMENTS = "departments";
        public static final String TIMESHEET = "timesheet";
        public static final String USER = "user";
        public static final String RECORDS = "records";
        public static final String PERIOD_DISPLAY = "periodDisplay";
        public static final String USER_ID = "userId";
        public static final String PRESENCES = "presences";
    }
    public static class Jwt{
        public static final String BEARER = "Bearer ";
    }
}
