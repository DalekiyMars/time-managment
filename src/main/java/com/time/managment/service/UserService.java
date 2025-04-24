package com.time.managment.service;

import com.time.managment.constants.Constants;
import com.time.managment.constants.Role;
import com.time.managment.dto.HandlerDto;
import com.time.managment.dto.UserDTO;
import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.User;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.mapper.UserMapper;
import com.time.managment.repository.UserRepository;
import com.time.managment.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;

    public User getUser(Integer timeSheet) {
        return userRepository
                .findByTimeSheet(timeSheet)
                .orElseThrow(() -> new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT));
    }

    public UserDTO getUserDTO(Integer timeSheet) {
        return userMapper.toUserDto(getUser(timeSheet));
    }

    @Transactional
    public SecurityUser saveUser(User user) {
        if (!userRepository.existsByTimeSheet(user.getTimeSheet())) {
            userRepository.save(user);

            // Генерация логина для SecurityUser
            final String generatedUsername = generateUsername(user.getUsername(), user.getTimeSheet());

            final var generatedPassword = PasswordGenerator.generate();
            // Создание связанного SecurityUser
            SecurityUser secUser = new SecurityUser().setUsername(generatedUsername)
                                                    .setPassword(passwordEncoder.encode(generatedPassword))
                                                    .setTimesheet(user.getTimeSheet());
            secUser.getRoles().add(Role.USER); // Назначаем базовую роль

            securityUserService.save(secUser);
            log.info(String.format("User создан: %s с паролем %s", secUser.getUsername(), generatedPassword));
            return new SecurityUser()
                    .setUsername(secUser.getUsername())
                    .setPassword(generatedPassword);
        } else {
            log.error("Error saving user " + user);
            throw new SomethingWentWrong(Constants.ExceptionMessages.TIMESHEET_ALREADY_EXISTS);
        }
    }

    @Transactional
    public UserDTO updateUserForREST(Integer timeSheet, String newUsername, String newRole) {
        final var existingUser = updateUser(timeSheet, newUsername, newRole);

        return userMapper.toUserDto(existingUser);
    }

    public HandlerDto updateUserForModel(Integer timeSheet, String newUsername, String newRole) {
        try {
            updateUser(timeSheet,newUsername, newRole);

            return new HandlerDto()
                    .setSuccess(true)
                    .setMessage("Обновлено: " + newUsername);

        } catch (Exception e) {
            return new HandlerDto()
                    .setSuccess(false)
                    .setMessage("Ошибка обновления: " + e.getMessage());
        }
    }

    private User updateUser(Integer timeSheet, String newUsername, String newRole){
        final User existingUser = userRepository.findByTimeSheet(timeSheet)
                .orElseThrow(() -> new NoSuchElementException("User not found with timesheet: " + timeSheet));

        existingUser.setUsername(newUsername);

        final SecurityUser secUser = securityUserService.getSecUserByTimeSheet(timeSheet);
        secUser.setRoles(new HashSet<>(List.of(Role.valueOf(newRole.toUpperCase()))));
        secUser.setUser(existingUser);

        existingUser.setSecurityUser(secUser);

        securityUserService.save(secUser);
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUserForREST(Integer timeSheet) {
        try {
            userRepository.deleteByTimeSheet(timeSheet);
        } catch (Exception ex){
            throw new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT);
        }
    }

    @Transactional
    public HandlerDto deleteUserForModel(Integer timeSheet) {
        try {
            userRepository.deleteByTimeSheet(timeSheet);
            return new HandlerDto()
                    .setSuccess(true)
                    .setMessage("Пользователь удалён.");
        } catch (Exception ex) {
            return new HandlerDto()
                    .setSuccess(false)
                    .setMessage("Ошибка удаления: " + Constants.ExceptionDescriptions.NO_SUCH_ELEMENT);
        }
    }

    public List<UserDTO> getAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private String generateUsername(String fullName, Integer timesheet) {
        // Находим все слова, начинающиеся с заглавной
        final List<String> parts = Arrays.stream(fullName.trim().split("(?=\\p{Lu})"))
                .filter(s -> !s.isBlank())
                .toList();

        final String lastName = parts.get(0); // Фамилия с заглавной
        final String name = parts.get(1);     // Имя с заглавной

        return lastName + timesheet + name;
    }
}
