package com.time.managment.service;

import com.time.managment.entity.SecurityUser;
import com.time.managment.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService {
    private final SecurityUserRepository userRepository;

    public void save(SecurityUser user){
        userRepository.save(user);
    }

    public SecurityUser getSecUserByTimeSheet(Integer timeSheet){
        return userRepository.findByTimesheet(timeSheet);
    }
}
