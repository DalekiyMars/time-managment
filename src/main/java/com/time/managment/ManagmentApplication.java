package com.time.managment;

import com.time.managment.entity.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class ManagmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagmentApplication.class, args);
    }

}
