package com.time.managment.controller;

import com.time.managment.constants.Role;
import com.time.managment.entity.SecurityUser;
import com.time.managment.service.SecurityUserService;
import com.time.managment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final SecurityUserService securityUserService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new SecurityUser());
        return "register";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // имя твоего шаблона home.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") SecurityUser user, Model model) {
        // Проверка наличия пользователя с таким именем
        if (securityUserService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Имя пользователя уже занято. Пожалуйста, выберите другое.");
            return "register";
        } else if (!userService.existsByTimesheet(user.getTimesheet())) {
            model.addAttribute("error", "Пользователя с таким табельным номером не существует.");
            return "register";
        }
        // Присваиваем роль по умолчанию
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER));  // Роль по умолчанию
        securityUserService.save(user);

        return "redirect:/login";
    }
}
