package com.time.managment.security;

import com.time.managment.constants.Role;
import com.time.managment.entity.AdminProperties;
import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.User;
import com.time.managment.repository.SecurityUserRepository;
import com.time.managment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final SecurityUserRepository securityUserRepository;
    private final UserRepository userRepository;
    private final AdminProperties adminProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("🧪 Config: username={}, password={}, timesheet={}, fullUserInitials={}",
                adminProperties.getUsername(),
                adminProperties.getPassword(),
                adminProperties.getTimesheet(),
                adminProperties.getFullUserInitials());
        
        if (securityUserRepository.findByUsername(adminProperties.getUsername()).isEmpty()) {
            User user = new User()
                            .setUsername(adminProperties.getFullUserInitials())
                            .setTimeSheet(adminProperties.getTimesheet());

            SecurityUser admin = new SecurityUser()
                    .setUsername(adminProperties.getUsername())
                    .setPassword(passwordEncoder.encode(adminProperties.getPassword()))
                    .setTimesheet(adminProperties.getTimesheet())
                    .setUser(user);
            admin.getRoles().add(Role.ADMIN);
            user.setSecurityUser(admin);
            userRepository.save(user);

            log.info("✅ Администратор создан: " + admin.getUsername());
        } else {
            log.error("ℹ️ Администратор уже существует.");
        }
    }
}