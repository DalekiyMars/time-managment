package com.time.managment.controller;

import com.time.managment.dto.UserDTO;
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
        model.addAttribute("user", new User());
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
            User user = new User()
                    .setUsername(username)
                    .setTimeSheet(timeSheet);

            var saved = userService.saveUser(user);
            model.addAttribute("message", "Пользователь добавлен: логин — " + saved.getUsername() +
                    ", пароль — " + saved.getPassword());
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка при добавлении пользователя: " + e.getMessage());
        }
        return "user-add";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{timesheet}")
    public String editUser(@PathVariable Integer timesheet, Model model) {
        try {
            UserDTO userDTO = userService.getUserDTO(timesheet);
            User user = new User()
                    .setUsername(userDTO.getUsername())
                    .setTimeSheet(userDTO.getTimeSheet());
            model.addAttribute("user", user);
            return "user-update";
        } catch (Exception e) {
            model.addAttribute("message", "Пользователь не найден.");
            return "redirect:/users/form";
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, Model model) {
        try {
            UserDTO updated = userService.updateUser(user.getTimeSheet(), user);
            model.addAttribute("message", "Обновлено: " + updated.getUsername());
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка обновления: " + e.getMessage());
            model.addAttribute("success", false);
        }
        return "user-update";
    }
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Integer timeSheet, Model model) {
        try {
            userService.deleteUser(timeSheet);
            model.addAttribute("message", "Пользователь удалён.");
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка удаления: " + e.getMessage());
            model.addAttribute("success", false);
        }
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
        model.addAttribute("users", userService.getAll()); // предположим, есть такой метод
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
            UserDTO user = userService.getUserDTO(timeSheet);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Пользователь не найден.");
        }
        return "user-search";
    }
}
