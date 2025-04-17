package com.time.managment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_departments")
public class UserDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_timesheet", referencedColumnName = "timesheet")
    private SecurityUser user;

    @Column(name = "department_number", nullable = false)
    private Integer departmentNumber;
}
