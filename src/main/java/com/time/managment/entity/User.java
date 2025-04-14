package com.time.managment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "initials")
    @Size(min = 3, max = 200, message = "Your name is too short or too long. Rewrite it")
    private String username;

    @NotNull
    @Column(name = "timesheet")
    private Integer timeSheet;
}
