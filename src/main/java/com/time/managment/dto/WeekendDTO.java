package com.time.managment.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
public class WeekendDTO {
    private UserDTO user;
    private String reason;
    private LocalDate weekendDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
