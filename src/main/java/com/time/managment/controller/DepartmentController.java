package com.time.managment.controller;

import com.time.managment.dto.DepartmentCreateRequest;
import com.time.managment.dto.DepartmentDTO;
import com.time.managment.entity.Department;
import com.time.managment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/{timeSheet}")
    public List<DepartmentDTO> getUser(@PathVariable("timeSheet") Integer timeSheet) {
        return departmentService.getDepartment(timeSheet);
    }

    @PostMapping("/save-to/")
    public ResponseEntity<Department> saveDepartment(@RequestBody DepartmentCreateRequest request) {
        Department created = departmentService.saveDepartment(request.getTimeSheet(), request.getDepartmentNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
