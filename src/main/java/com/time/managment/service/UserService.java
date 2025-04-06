package com.time.managment.service;

import com.time.managment.dto.UserDTO;
import com.time.managment.entity.User;
import com.time.managment.constants.Constants;
import com.time.managment.exceptions.SomethingWentWrong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.time.managment.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.time.managment.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUser(Integer userId){
        return userRepository
                .findById(userId)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT));
    }

    public UserDTO saveUser(User user){
        try {
            return userMapper.toUserDto(userRepository.save(user));
        } catch (Exception e){
            log.error("Error saving user " + user);
            throw new SomethingWentWrong(e.getMessage());
        }
    }
    public UserDTO updateUser(Integer targetTimeSheet, User updatedUser) {
        if (!userRepository.existsByTimeSheet(targetTimeSheet)){
            throw new NoSuchElementException(
                    "User not found with timeSheet: " + targetTimeSheet
            );
        }
        updatedUser.setTimeSheet(targetTimeSheet);
        return saveUser(updatedUser);
    }
}
