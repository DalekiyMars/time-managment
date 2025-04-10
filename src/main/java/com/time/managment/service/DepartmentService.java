package com.time.managment.service;

import com.time.managment.dto.DepartmentDTO;
import com.time.managment.entity.Department;
import com.time.managment.mapper.DepartmentMapper;
import com.time.managment.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper mapper;
    private final DepartmentRepository repository;
    private final UserService userService;

    public List<DepartmentDTO> getDepartment(Integer timesheet) {
        return repository.findAllByUserTimeSheet_TimeSheet(timesheet)
                .stream()
                .map(mapper::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    public Department saveDepartment(Integer timeSheet, Integer departmentNumber) {
        Department department = new Department(departmentNumber);
        department.setUserTimeSheet(userService.getUser(timeSheet));

        return repository.save(department);
    }


}
