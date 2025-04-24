package com.time.managment.service;

import com.time.managment.entity.Department;
import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.User;
import com.time.managment.entity.UserDepartment;
import com.time.managment.repository.SecurityUserRepository;
import com.time.managment.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component("accessService")
public class AccessService {

    private final SecurityUserRepository securityUserRepository;
    private final UserRepository userRepository;

    public AccessService(SecurityUserRepository securityUserRepository, UserRepository userRepository) {
        this.securityUserRepository = securityUserRepository;
        this.userRepository = userRepository;
    }

    public boolean hasAccessToUser(Integer targetTimeSheet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }

        final String currentUsername = authentication.getName();
        final SecurityUser currentUser = securityUserRepository.findByUsername(currentUsername)
                .orElse(null);
        if (currentUser == null) {
            return false;
        }

        final Set<Integer> managerDepartments = currentUser.getUserDepartments().stream()
                .map(UserDepartment::getDepartmentNumber)
                .collect(Collectors.toSet());

        final User targetUser = userRepository.findByTimeSheet(targetTimeSheet)
                .orElse(null);
        if (Objects.isNull(targetUser) || Objects.isNull(targetUser.getDepartments())) {
            return false;
        }

        final Set<Integer> userDepartments = targetUser.getDepartments().stream()
                .map(Department::getDepartment)
                .collect(Collectors.toSet());

        return !Collections.disjoint(managerDepartments, userDepartments);
    }

    public boolean isSelf(Integer timeSheet) {
        SecurityUser currentUser = getCurrentUser();
        return currentUser != null && currentUser.getTimesheet().equals(timeSheet);
    }

    private SecurityUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof SecurityUser)) return null;
        return (SecurityUser) auth.getPrincipal();
    }
}
