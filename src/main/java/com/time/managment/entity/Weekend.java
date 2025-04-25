package com.time.managment.entity;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.validator.ValidAbsenceReason;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "weekends")
@NoArgsConstructor
@Accessors(chain = true)
public class Weekend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_timesheet")
    @NotNull
    private Integer userTimeSheet;

    @Column(name = "reason")
    @ValidAbsenceReason
    @Enumerated(EnumType.STRING)
    private AbsenceReason reason;

    @Column(name = "weekend_date", columnDefinition = "DATE")
    @NotNull
    private LocalDate weekendDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public Weekend(Integer userTimeSheet, AbsenceReason reason, LocalDate weekendDate, LocalTime startTime, LocalTime endTime) {
        this.userTimeSheet = userTimeSheet;
        this.reason = reason;
        this.weekendDate = weekendDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setReason(String reason) {
        this.reason = AbsenceReason.fromString(reason);
    }
}
