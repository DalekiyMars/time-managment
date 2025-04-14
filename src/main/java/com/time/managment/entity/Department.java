package com.time.managment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_timesheet", referencedColumnName = "timesheet")
    private User userTimeSheet;

    @NotNull
    @Column(name = "department")
    private Integer department;

    public Department(Integer department) {
        this.department = department;
    }
}
