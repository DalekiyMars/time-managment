package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.DepartmentDTO;
import com.time.managment.dto.HandlerDto;
import com.time.managment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/get")
    public String showForm(Model model, @RequestParam(value = "timesheet", required = false) Integer timesheet) {
        if (Objects.nonNull(timesheet)) {
            final List<DepartmentDTO> departments = departmentService.getDepartment(timesheet);
            model.addAttribute(Constants.ModelValues.DEPARTMENTS, departments);
        }
        return "departments-search";
    }

    @GetMapping("/add")
    public String showForm() {
        return "department-add";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/save-to")
    public String saveDepartmentViaForm(@RequestParam Integer timeSheet,
                                        @RequestParam Integer departmentNumber,
                                        Model model) {
        setMessageAndSuccess(model, departmentService.saveDepartmentForModel(timeSheet, departmentNumber));

        return "department-add";
    }

    private void setMessageAndSuccess(Model model, HandlerDto result){
        model.addAttribute(Constants.ModelValues.MESSAGE, result.getMessage());
        model.addAttribute(Constants.ModelValues.SUCCESS, result.getSuccess());
    }

    @GetMapping("/delete-form")
    public String showDeleteForm() {
        return "department-delete";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timeSheet))")
    @PostMapping("/delete-form")
    public String deleteDepartment(@RequestParam Integer timeSheet,
                                   @RequestParam Integer departmentNumber,
                                   Model model) {
        setMessageAndSuccess(model, departmentService.deleteByTimesheetAndDepartmentForView(timeSheet, departmentNumber));

        return "department-delete";
    }
}