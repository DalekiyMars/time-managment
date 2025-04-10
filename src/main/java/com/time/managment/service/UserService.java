package com.time.managment.service;

import com.time.managment.constants.Constants;
import com.time.managment.dto.UserDTO;
import com.time.managment.entity.User;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.mapper.UserMapper;
import com.time.managment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUser(Integer timeSheet) {
        return userRepository
                .findByTimeSheet(timeSheet)
                .orElseThrow(() -> new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT));
    }

    public UserDTO getUserDTO(Integer timeSheet) {
        return userMapper.toUserDto(getUser(timeSheet));
    }

    public UserDTO saveUser(User user) {
        if (!userRepository.existsByTimeSheet(user.getTimeSheet())) {
            return userMapper.toUserDto(userRepository.save(user));
        } else {
            log.error("Error saving user " + user);
            throw new SomethingWentWrong("User with this timesheet already exists");
        }
    }

    @Transactional
    public UserDTO updateUser(Integer timeSheet, User updatedUser) {
        User existingUser = userRepository.findByTimeSheet(timeSheet)
                .orElseThrow(() -> new NoSuchElementException("User not found with timesheet: " + updatedUser.getTimeSheet()));

        existingUser.setUsername(updatedUser.getUsername());
        return userMapper.toUserDto(userRepository.save(existingUser));
    }
}
