package com.time.managment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.time.managment.constants.AbsenceReason;
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
    private AbsenceReason reason;
    @NotNull
    private LocalDate weekendDate;
    private LocalTime startTime;
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
        this.reason = AbsenceReason.fromString(reason);
        this.weekendDate = weekendDate;
    }

    public String getReasonDisplay() {
        return reason != null ? reason.getDisplayName() : "";
    }

}
