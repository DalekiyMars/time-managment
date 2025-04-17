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
        // Допустим, ты уже получаешь пользователя по timesheet
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet); // Заглушка. На самом деле ты должен получить пользователя из БД.

        userDepartmentService.addDepartmentToUser(user, departmentNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{timesheet}")
    public ResponseEntity<List<UserDepartment>> getDepartmentsForUser(@PathVariable Integer timesheet) {
        // Допустим, ты уже получаешь пользователя по timesheet
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet); // Заглушка, на самом деле, нужно получить из БД.

        List<UserDepartment> departments = userDepartmentService.getDepartmentsForUser(user);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{timesheet}/{departmentNumber}")
    public ResponseEntity<Void> removeDepartmentFromUser(@PathVariable Integer timesheet, @PathVariable Integer departmentNumber) {
        // Получение пользователя и удаление департамента
        SecurityUser user = new SecurityUser();
        user.setTimesheet(timesheet); // Заглушка

        userDepartmentService.removeDepartmentFromUser(user, departmentNumber);
        return ResponseEntity.noContent().build();
    }
}
