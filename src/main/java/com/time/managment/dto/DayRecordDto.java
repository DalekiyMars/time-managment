package com.time.managment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DayRecordDto {
    private LocalDate date;
    private String type; // "presence" или "weekend"
    private LocalDateTime presenceTime; // только если type = presence
    private String weekendReason; // только если type = weekend
}