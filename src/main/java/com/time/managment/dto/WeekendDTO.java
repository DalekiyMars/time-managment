package com.time.managment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
public class WeekendDTO {
    @NotNull
    private Integer userTimeSheet;
    @NotBlank
    private String reason;
    @NotNull
    private LocalDate weekendDate;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;


    @JsonCreator
    public WeekendDTO(@JsonProperty("userTimesheet") Integer userTimesheet,
                      @JsonProperty("startTime") LocalTime startTime,
                      @JsonProperty("endTime") LocalTime endTime,
                      @JsonProperty("reason") String reason,
                      @JsonProperty("weekendDate") LocalDate weekendDate) {
        this.userTimeSheet = userTimesheet;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.weekendDate = weekendDate;
    }
}
