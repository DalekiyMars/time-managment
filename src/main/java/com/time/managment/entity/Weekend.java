package com.time.managment.entity;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.validator.ValidAbsenceReason;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "weekends")
public class Weekend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_timesheet")
    private Integer userTimeSheet;

    public void setReason(String reason) {
        this.reason = AbsenceReason.fromString(reason);
    }

    @Column(name = "reason")
    @ValidAbsenceReason
    @Enumerated(EnumType.STRING) // Храним строковое значение перечисления в базе данных
    private AbsenceReason reason;

    @Column(name = "weekend_date", columnDefinition = "DATE")
    private LocalDate weekendDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
