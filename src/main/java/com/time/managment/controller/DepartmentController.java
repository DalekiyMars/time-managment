package com.time.managment.controller;

import com.time.managment.dto.DepartmentDTO;
import com.time.managment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println(">>> Контроллер вызван, timesheet = " + timesheet);

        if (timesheet != null) {
            List<DepartmentDTO> departments = departmentService.getDepartment(timesheet);
            model.addAttribute("departments", departments);
        }
        return "departments"; // шаблон departments.html
    }
}