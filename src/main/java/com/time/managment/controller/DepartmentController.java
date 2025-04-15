package com.time.managment.controller;

import com.time.managment.dto.DepartmentDTO;
import com.time.managment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get")
    public String showForm(Model model, @RequestParam(value = "timesheet", required = false) Integer timesheet) {
        if (timesheet != null) {
            List<DepartmentDTO> departments = departmentService.getDepartment(timesheet);
            model.addAttribute("departments", departments);
        }
        return "departments";
    }

    @GetMapping("/add")
    public String showForm() {
        return "department-add";
    }

    @PostMapping("/save-to/")
    public String saveDepartmentViaForm(@RequestParam Integer timeSheet,
                                        @RequestParam Integer departmentNumber,
                                        Model model) {
        try {
            DepartmentDTO created = departmentService.saveDepartment(timeSheet, departmentNumber);
            model.addAttribute("message", "Успешно сохранено: " + created.getUser().getUsername()
                    + " (" + created.getUser().getTimeSheet() + "), отдел: " + created.getDepartment());
            model.addAttribute("success", true);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка: " + ex.getMessage());
            model.addAttribute("success", false);
        }
        return "department-add";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm() {
        return "department-delete";
    }

    @PostMapping("/delete-form")
    public String deleteDepartment(@RequestParam Integer timeSheet,
                                   @RequestParam Integer departmentNumber,
                                   Model model) {
        try {
            departmentService.deleteByTimesheetAndDepartment(timeSheet, departmentNumber);
            model.addAttribute("message", "Успешно удалено: табельный номер " + timeSheet + ", отдел " + departmentNumber);
            model.addAttribute("success", true);
        } catch (Exception ex) {
            model.addAttribute("message", "Ошибка при удалении: " + ex.getMessage());
            model.addAttribute("success", false);
        }
        return "department-delete";
    }
}