package com.time.managment.service;

import com.time.managment.CommonTestStarter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends CommonTestStarter {
    @Autowired
    UserService userService;

    Integer validUserId = 10;
    Integer invalidUserId = 100;

    @Test
    @DisplayName("Получение существующего пользователя")
    void getUser_UserAllowed() {

    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void getUser_UserNotAllowed() {
        assertThrows(NoSuchElementException.class, () -> userService.getUser(invalidUserId));
    }

    @Test
    void saveUser() {
    }

    @Test
    void updateUser() {
    }
}