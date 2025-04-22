package com.time.managment.controller;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user-departments")
@RequiredArgsConstructor
public class UserDepartmentController {
    private final UserDepartmentService userDepartmentService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{timesheet}")
    public String getDepartmentsForUser(@PathVariable Integer timesheet, Model model) {
        // Получаем все департаменты для пользователя с данным timesheet
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet);

        List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);

        model.addAttribute("departments", departments);
        model.addAttribute("timesheet", timesheet);

        return "add-user-department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Добавление департамента пользователю
    @PostMapping("/{timesheet}")
    public String addDepartmentToUser(@PathVariable Integer timesheet,
                                      @RequestParam Integer departmentNumber) {
        // Создаем объект пользователя с указанным timesheet
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet);

        try {
            userDepartmentService.addDepartmentToUser(user, departmentNumber);
        } catch (Exception e)
        {
            throw new SomethingWentWrong("Что-то пошло не так, перепроверьте введенные данные");
        }
        // Добавляем департамент пользователю

        // Перенаправляем на страницу с департаментами
        return "redirect:/user-departments/" + timesheet;
    }

    // Удаление департамента у пользователя
    @PostMapping("/{timesheet}/{departmentNumber}")
    public String removeDepartmentFromUser(@PathVariable Integer timesheet,
                                           @PathVariable Integer departmentNumber) {
        // Создаем объект пользователя с указанным timesheet
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet);

        // Удаляем департамент
        userDepartmentService.removeDepartmentFromUser(user, departmentNumber);

        // Перенаправляем на страницу с департаментами
        return "redirect:/user-departments/" + timesheet;
    }
}
