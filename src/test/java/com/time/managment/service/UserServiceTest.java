package com.time.managment.service;

import com.time.managment.CommonTestStarter;
import com.time.managment.dto.UserDTO;
import com.time.managment.entity.User;
import com.time.managment.mapper.UserMapper;
import com.time.managment.utils.JsonUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "/dataSource/fill-tables.sql",
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserServiceTest extends CommonTestStarter {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    String BASE_ENTITY_PATH = "src/test/resources/user/";

    UserDTO getTestUserDTO() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "AllowedUserDto.json", UserDTO.class);
    }

    User getTestUser() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "AllowedUser.json", User.class);
    }

    User getBrokenUser() throws IOException {
        return JsonUtils.convertJsonFromFileToObject(BASE_ENTITY_PATH + "BrokenUser.json", User.class);
    }

    @Test
    @DisplayName("Получение существующего пользователя")
    void getUser_UserAllowed() throws IOException {
        var testUser = getTestUserDTO();
        var userFromDb = userService.getUserDTO(testUser.getTimeSheet());

        Assertions.assertEquals(testUser, userFromDb);
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    void getUser_UserNotAllowed() {
        assertThrows(NoSuchElementException.class, () -> userService.getUser(-99919));
    }

    @Test
    @DisplayName("Сохранение корректного пользователя")
    void saveUser_UserAllowed() throws IOException {
        var answer = userService.saveUser(getTestUser());
        //Assertions.assertEquals(answer, getTestUserDTO());
    }

    @Test
    @DisplayName("Сохранение некорректного пользователя")
    void saveUser_UserNotAllowed() throws IOException {
        var incorrectUser = getBrokenUser();
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.saveUser(incorrectUser));
    }

    @Test
    @DisplayName("Корректное обновление пользователя")
    void updateUser_UserAllowed() throws IOException {
        var testUser = getTestUser();
        var except = userService.updateUserForREST(123456, testUser.getUsername(), "USER");
        var user = getTestUserDTO();
        user.setTimeSheet(except.getTimeSheet());
        Assertions.assertEquals(user, except);
    }

    @Test
    @DisplayName("Некорректное обновление пользователя")
    void updateUser_UserNotAllowed() throws IOException {
        var user = getBrokenUser();
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.updateUserForREST(user.getTimeSheet(), user.getUsername(), "USER"));
    }
}