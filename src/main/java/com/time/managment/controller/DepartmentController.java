package com.time.managment.controller;

import com.time.managment.constants.Constants;
import com.time.managment.dto.DepartmentCreateRequest;
import com.time.managment.dto.DepartmentDTO;
import com.time.managment.service.DepartmentService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/departments/form")
public class DepartmentController {
    private final RestTemplate restTemplate;
    private final DepartmentService departmentService;

    @GetMapping("/get")
    public String showDepartments(@RequestParam(value = "timesheet", required = false) Integer timesheet,
                                  Model model) {
        model.addAttribute("timesheet", timesheet);

        try {
            ResponseEntity<DepartmentDTO[]> response = restTemplate.getForEntity(
                    Constants.Urls.DEPARTMENTS_FORM_URL + "/" + timesheet, DepartmentDTO[].class);
            List<DepartmentDTO> departments = Arrays.asList(Objects.requireNonNull(response.getBody()));
            model.addAttribute("departments", departments);
        } catch (HttpClientErrorException e) {
            model.addAttribute("departments", Collections.emptyList());
            model.addAttribute("errorMessage", "Ошибка при получении данных: " + e.getStatusCode());
        }

        return "departments";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("departmentCreateRequest", new DepartmentCreateRequest());
        return "department-add";
    }

    @PostMapping("/save")
    public String saveDepartment(
            @ModelAttribute("departmentCreateRequest") DepartmentCreateRequest request,
            Model model) {
        try {
            departmentService.saveDepartment(request.getTimeSheet(), request.getDepartmentNumber());
            model.addAttribute("successMessage", "Отдел успешно добавлен!");
        } catch (EntityExistsException e) {
            model.addAttribute("errorMessage", "Такой отдел уже существует для этого табельного номера.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении: " + e.getMessage());
        }

        model.addAttribute("departmentCreateRequest", new DepartmentCreateRequest()); // сброс формы
        return "department-add";
    }
}