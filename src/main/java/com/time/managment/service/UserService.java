package com.time.managment.service;

import com.time.managment.constants.Constants;
import com.time.managment.constants.Role;
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
            String generatedUsername = generateUsername(user.getUsername(), user.getTimeSheet());

            var generatedPassword = PasswordGenerator.generate();
            // Создание связанного SecurityUser
            SecurityUser secUser = new SecurityUser().setUsername(generatedUsername)
                                                    .setPassword(passwordEncoder.encode(generatedPassword))
                                                    .setTimesheet(user.getTimeSheet());
            secUser.getRoles().add(Role.USER); // Назначаем базовую роль

            securityUserService.save(secUser);
            log.info(String.format("User создан: %s с паролем %s", secUser.getUsername(), generatedPassword));
            return secUser.setPassword(generatedPassword);
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

    public List<UserDTO> getAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Генерация логина на основе ФИО и табельного номера.
     * Пример: ИвановИванИванович + 12345 → ivanov.12345
     */
    private String generateUsername(String fullName, Integer timesheet) {
        // Находим все слова, начинающиеся с заглавной
        List<String> parts = Arrays.stream(fullName.split("(?=\\p{Lu})"))
                .filter(s -> !s.isBlank())
                .toList();

        if (parts.size() < 2) {
            throw new SomethingWentWrong("Формат ФИО некорректный: " + fullName);
        }

        String lastName = parts.get(0).toLowerCase(); // Фамилия
        return lastName + "." + timesheet;
    }
}
