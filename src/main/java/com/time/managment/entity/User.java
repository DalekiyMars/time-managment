package com.time.managment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Accessors(chain = true)
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
    @Min(value = 100, message = "Слишком короткий табельный номер")
    @Max(value = 999999,message = "Слишком длинный табельный номер")
    private Integer timeSheet;

    @OneToMany(mappedBy = "userTimeSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SecurityUser securityUser;
}
