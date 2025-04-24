package com.time.managment.dto;

import com.time.managment.constants.Constants;
import com.time.managment.exceptions.SomethingWentWrong;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@AllArgsConstructor
public class WeekendToDelete {
    @NotNull
    private Integer timeSheet;

    public LocalDate getWeekendDate() {
        try {
            return LocalDate.parse(this.weekendDate);
        } catch (DateTimeParseException e) {
            throw new SomethingWentWrong(Constants.ExceptionMessages.DATE_CONVERTING_EXCEPTION);
        }
    }

    @NotEmpty
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
            message = "Дата должна быть в формате ГГГГ-ММ-ДД"
    )
    private String weekendDate;
}
