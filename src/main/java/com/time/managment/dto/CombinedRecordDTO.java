package com.time.managment.dto;

import com.time.managment.constants.AbsenceReason;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CombinedRecordDTO {
    private String type; // "presence" или "weekend"
    private Integer userId;
    private LocalDate date; // Только дата, без времени
    private LocalTime startTime; // Время начала
    private LocalTime endTime;   // Время окончания
    private AbsenceReason reason; // Только для weekend

    public String getReadableType() {
        return switch (type) {
            case "presence" -> "Присутствие";
            case "weekend" -> "Выходной";
            default -> type;
        };
    }

    public String getReason() {
        return reason != null ? reason.getDisplayName() : "-";
    }
}