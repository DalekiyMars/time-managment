package com.time.managment.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin")
@Getter
@Setter
public class AdminProperties {
    private String username;
    private String password;
    private Integer timesheet;

    private String fullUserInitials;
}