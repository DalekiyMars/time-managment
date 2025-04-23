package com.time.managment.controller;

import com.time.managment.constants.Role;
import com.time.managment.entity.SecurityUser;
import com.time.managment.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final SecurityUserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Отображение формы изменения роли для выбранного пользователя
    @GetMapping("/changeRole/{id}")
    public String showChangeRoleForm(@PathVariable Integer id, Model model) {
        final Optional<SecurityUser> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден");
            return "redirect:/admin/users";
        }
        model.addAttribute("user", optUser.get());
        // Предоставляем список доступных ролей
        model.addAttribute("roles", Role.values());
        return "admin-change-role";
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Обработка изменения роли
    @PostMapping("/changeRole")
    public String changeUserRole(@RequestParam Integer id, @RequestParam Role role, Model model) {
        final Optional<SecurityUser> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден");
            return "redirect:/admin/users";
        }
        final SecurityUser user = optUser.get()
                .setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}
