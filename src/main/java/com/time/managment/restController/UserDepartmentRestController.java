package com.time.managment.restController;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-departments")
@RequiredArgsConstructor
public class UserDepartmentRestController {
    private final UserDepartmentService userDepartmentService;
    @PostMapping("/{timesheet}")
    public ResponseEntity<Void> addDepartmentToUser(@PathVariable Integer timesheet, @RequestBody Integer departmentNumber) {
        final var user = new SecurityUser()
                .setTimesheet(timesheet);

        userDepartmentService.addDepartmentToUser(user, departmentNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{timesheet}")
    public ResponseEntity<List<UserDepartment>> getDepartmentsForUser(@PathVariable Integer timesheet) {
        final var user = new SecurityUser()
                .setTimesheet(timesheet);

        List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{timesheet}/{departmentNumber}")
    public ResponseEntity<Void> removeDepartmentFromUser(@PathVariable Integer timesheet, @PathVariable Integer departmentNumber) {
        final var user = new SecurityUser()
                .setTimesheet(timesheet);

        userDepartmentService.removeDepartmentFromUserForREST(user, departmentNumber);
        return ResponseEntity.noContent().build();
    }
}
