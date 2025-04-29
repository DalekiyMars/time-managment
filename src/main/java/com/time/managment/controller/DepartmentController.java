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

@RequiredArgsConstructor
@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
    @GetMapping("/get-form")
    public String getEmptyForm(){
        return "departments-search";
    }

    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('MANAGER') and @accessService.hasAccessToUser(#timesheet)) " +
            "or (hasRole('USER') and @accessService.isSelf(#timesheet))")
    @PostMapping("/get")
    public String showForm(Model model, @RequestParam(value = "timesheet") Integer timesheet) {
        final List<DepartmentDTO> departments = departmentService.getDepartment(timesheet);
        model.addAttribute(Constants.ModelValues.DEPARTMENTS, departments);

        return "departments-search";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
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

    @PreAuthorize("hasAnyRole('MANAGER', 'USER', 'ADMIN')")
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