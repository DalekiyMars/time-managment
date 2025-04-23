package com.time.managment.controller;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user-departments")
@RequiredArgsConstructor
public class UserDepartmentController {
    private final UserDepartmentService userDepartmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/manage")
    public String showForm(@RequestParam(value = "timesheet", required = false) Integer timesheet, Model model) {
        if (Objects.nonNull(timesheet)) {
            final var user = new SecurityUser().setTimesheet(timesheet);
            final List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
            model.addAttribute("departments", departments);
            model.addAttribute("timesheet", timesheet);
        }
        return "add-user-department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addDepartmentToUser(@RequestParam Integer timesheet,
                                      @RequestParam Integer departmentNumber,
                                      Model model) {
        final var user = new SecurityUser().setTimesheet(timesheet);
        try {
            userDepartmentService.addDepartmentToUser(user, departmentNumber);
            model.addAttribute("message", "Департамент успешно добавлен!");
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка добавления департамента: " + e.getMessage());
            model.addAttribute("success", false);
        }

        // Обновляем список департаментов
        final List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        model.addAttribute("departments", departments);
        model.addAttribute("timesheet", timesheet);
        return "add-user-department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String removeDepartmentFromUser(@RequestParam Integer timesheet,
                                           @RequestParam Integer departmentNumber,
                                           Model model) {
        SecurityUser user = new SecurityUser().setTimesheet(timesheet);
        try {
            userDepartmentService.removeDepartmentFromUser(user, departmentNumber);
            model.addAttribute("message", "Департамент успешно удалён.");
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка удаления департамента: " + e.getMessage());
            model.addAttribute("success", false);
        }

        final List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        model.addAttribute("departments", departments);
        model.addAttribute("timesheet", timesheet);
        return "add-user-department";
    }
}
