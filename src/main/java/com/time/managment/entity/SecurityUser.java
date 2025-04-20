package com.time.managment.entity;

import com.time.managment.constants.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sec_users")
@NoArgsConstructor
@Accessors(chain = true)
public class SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "timesheet", nullable = false, unique = true)
    private Integer timesheet;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sec_user_roles", joinColumns = @JoinColumn(name = "user_timesheet", referencedColumnName = "timesheet"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "timesheet", referencedColumnName = "timeSheet", insertable = false, updatable = false)
    private User user;
}
