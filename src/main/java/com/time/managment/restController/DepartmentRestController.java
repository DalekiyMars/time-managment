package com.time.managment.restController;

import com.time.managment.dto.DepartmentCreateRequest;
import com.time.managment.dto.DepartmentDTO;
import com.time.managment.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentRestController {
    private final DepartmentService departmentService;

    @GetMapping("/{timeSheet}")
    public List<DepartmentDTO> getUser(@PathVariable("timeSheet") Integer timeSheet) {
        return departmentService.getDepartment(timeSheet);
    }

    @PostMapping("/save-to/")
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentCreateRequest request) {
        DepartmentDTO created = departmentService.saveDepartment(request.getTimeSheet(), request.getDepartmentNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/by-timesheet-and-department")
    public ResponseEntity<Void> deleteDepartment(
            @RequestParam Integer timesheet,
            @RequestParam Integer departmentNumber
    ) {
        departmentService.deleteByTimesheetAndDepartment(timesheet, departmentNumber);
        return ResponseEntity.ok().build();
    }
}
