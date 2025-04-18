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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @Transactional
    public UserDTO saveUser(User user) {
        if (!userRepository.existsByTimeSheet(user.getTimeSheet())) {
            return userMapper.toUserDto(userRepository.save(user));
        } else {
            log.error("Error saving user " + user);
            throw new SomethingWentWrong(Constants.ExceptionMessages.TIMESHEET_ALREADY_EXISTS);
        }
    }

    @Transactional
    public UserDTO updateUser(Integer timeSheet, User updatedUser) {
        User existingUser = userRepository.findByTimeSheet(timeSheet)
                .orElseThrow(() -> new NoSuchElementException("User not found with timesheet: " + updatedUser.getTimeSheet()));

        existingUser.setUsername(updatedUser.getUsername());
        return userMapper.toUserDto(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser(Integer timeSheet) {
        try {
            userRepository.deleteByTimeSheet(timeSheet);
        } catch (Exception ex){
            throw new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT);
        }
    }

    public boolean existsByTimesheet(Integer timesheet){
        return userRepository.existsByTimeSheet(timesheet);
    }

    public List<UserDTO> getAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
