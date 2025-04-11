package com.time.managment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "presences")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_timesheet", referencedColumnName = "timesheet")
    private User userTimeSheet;

    @Column(name = "time_mark")
    private LocalDateTime timeMark;
}
