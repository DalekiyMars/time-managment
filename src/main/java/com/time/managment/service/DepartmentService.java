package com.time.managment.service;

import com.time.managment.dto.DepartmentDTO;
import com.time.managment.entity.Department;
import com.time.managment.entity.User;
import com.time.managment.mapper.DepartmentMapper;
import com.time.managment.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public DepartmentDTO saveDepartment(Integer timeSheet, Integer departmentNumber) {
        final User user = userService.getUser(timeSheet);
        final Optional<Department> existing = repository.findByUserTimeSheetAndDepartment(user, departmentNumber);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Такой отдел уже существует для указанного табельного номера.");
        }

        final Department department = new Department(departmentNumber)
                .setUserTimeSheet(user);

        return mapper.toDepartmentDTO(repository.save(department));
    }

    public void deleteByTimesheetAndDepartment(Integer timesheet, Integer departmentNumber) {
        final Department department = repository.findByUserTimeSheetAndDepartment(userService.getUser(timesheet), departmentNumber)
                .orElseThrow(() -> new RuntimeException("Department not found with timesheet: " + timesheet + " and department: " + departmentNumber));
        repository.delete(department);
    }
}
