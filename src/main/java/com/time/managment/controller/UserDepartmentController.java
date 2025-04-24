package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.HandlerDto;
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
            setDepartmentsAndTimesheet(model, departments, timesheet);
        }
        return "add-user-department";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addDepartmentToUser(@RequestParam Integer timesheet,
                                      @RequestParam Integer departmentNumber,
                                      Model model) {
        final var user = new SecurityUser().setTimesheet(timesheet);
        setMessageAndSuccessForModel(model, userDepartmentService.addDepartmentToUserForView(user, departmentNumber));

        // Обновляем список департаментов
        final List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        setDepartmentsAndTimesheet(model, departments, timesheet);

        return "add-user-department";
    }

    private void setMessageAndSuccessForModel(Model model, HandlerDto result){
        model.addAttribute(Constants.ModelValues.MESSAGE, result.getMessage());
        model.addAttribute(Constants.ModelValues.SUCCESS, result.getSuccess());
    }

    private void setDepartmentsAndTimesheet(Model model, List<UserDepartment> departments, Integer timesheet){
        model.addAttribute(Constants.ModelValues.DEPARTMENTS, departments);
        model.addAttribute(Constants.ModelValues.TIMESHEET, timesheet);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String removeDepartmentFromUser(@RequestParam Integer timesheet,
                                           @RequestParam Integer departmentNumber,
                                           Model model) {
        final var user = new SecurityUser().setTimesheet(timesheet);
        setMessageAndSuccessForModel(model, userDepartmentService.removeDepartmentFromUserForView(user, departmentNumber));

        final List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        setDepartmentsAndTimesheet(model, departments, timesheet);

        return "add-user-department";
    }
}
