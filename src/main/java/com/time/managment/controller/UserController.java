package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.HandlerDto;
import com.time.managment.entity.User;
import com.time.managment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/form")
    public String showUserForm(Model model) {
        model.addAttribute(Constants.ModelValues.USER, new User());
        return "user-form";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/add-form")
    public String showAddUserForm() {
        return "user-add";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                          @RequestParam Integer timeSheet,
                          Model model) {
        try {
            final var user = new User()
                    .setUsername(username)
                    .setTimeSheet(timeSheet);

            final var saved = userService.saveUser(user);
            model.addAttribute(Constants.ModelValues.MESSAGE, "Пользователь добавлен: логин — " + saved.getUsername() +
                    ", пароль — " + saved.getPassword());
        } catch (Exception e) {
            model.addAttribute(Constants.ModelValues.MESSAGE, "Ошибка при добавлении пользователя: " + e.getMessage());
        }
        return "user-add";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{timesheet}")
    public String editUser(@PathVariable Integer timesheet, Model model) {
        try {
            final var userDTO = userService.getUserDTO(timesheet);
            final var user = new User()
                    .setUsername(userDTO.getUsername())
                    .setTimeSheet(userDTO.getTimeSheet());
            model.addAttribute(Constants.ModelValues.USER, user);
            return "user-update";
        } catch (Exception e) {
            model.addAttribute(Constants.ModelValues.MESSAGE, "Пользователь не найден.");
            return "redirect:/users/form";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update-form")
    public String showUpdateForm(){
        return "user-update";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/update")
    public String updateUser(@RequestParam Integer timeSheet,
                             @RequestParam String username,
                             @RequestParam String role,
                             Model model) {
        setMessageAndSuccess(model, userService.updateUserForModel(timeSheet, username, role));

        return "user-update";
    }

    private void setMessageAndSuccess(Model model, HandlerDto result){
        model.addAttribute(Constants.ModelValues.MESSAGE, result.getMessage());
        model.addAttribute(Constants.ModelValues.SUCCESS, result.getSuccess());
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Integer timeSheet, Model model) {
        setMessageAndSuccess(model,userService.deleteUserForModel(timeSheet));
        return "user-delete";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/delete-form")
    public String showDeleteForm() {
        return "user-delete";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/search")
    public String searchUserForm() {
        return "user-search";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/search-result")
    public String searchUserByTimesheet(@RequestParam Integer timeSheet, Model model) {
        try {
            final var user = userService.getUserDTO(timeSheet);
            model.addAttribute(Constants.ModelValues.USER, user);
        } catch (Exception e) {
            model.addAttribute(Constants.ModelValues.ERROR_MESSAGE, "Пользователь не найден.");
        }
        return "user-search";
    }
}
