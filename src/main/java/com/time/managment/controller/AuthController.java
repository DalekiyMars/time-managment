package com.time.managment.controller;

import com.time.managment.constants.Role;
import com.time.managment.entity.SecurityUser;
import com.time.managment.repository.SecurityUserRepository;
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
    private final SecurityUserRepository userRepository;
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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") SecurityUser user, Model model) {
        // Проверка наличия пользователя с таким именем
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Имя пользователя уже занято. Пожалуйста, выберите другое.");
            return "register";
        }

        // Присваиваем роль по умолчанию
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER));  // Роль по умолчанию
        userRepository.save(user);

        return "redirect:/login";
    }
}
