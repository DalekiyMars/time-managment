package com.time.managment.service;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.repository.SecurityUserRepository;
import com.time.managment.repository.UserDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {
    private final UserDepartmentRepository userDepartmentRepository;
    private final SecurityUserRepository securityUserRepository;

    // Получение всех департаментов для пользователя
    public List<UserDepartment> getDepartmentsForUser(SecurityUser user) {
        return userDepartmentRepository.findByUser(user);
    }

    // Добавление департамента пользователю
    public void addDepartmentToUser(SecurityUser user, Integer departmentNumber) {
        if (userDepartmentRepository.existsByUser_TimesheetAndDepartmentNumber(user.getTimesheet(), departmentNumber)) {
            throw new SomethingWentWrong("У пользователя уже есть этот отдел!");
        }
        // Убедимся, что у пользователя правильно установлен timesheet
        if (Objects.isNull(user.getTimesheet())) {
            throw new IllegalArgumentException("User timesheet cannot be null.");
        }

        // Создаем объект UserDepartment с правильным timesheet
        final UserDepartment userDepartment = new UserDepartment()
                .setUser(securityUserRepository.findByTimesheet(user.getTimesheet()))
                .setDepartmentNumber(departmentNumber);

        // Сохраняем департамент
        userDepartmentRepository.save(userDepartment);
    }

    // Удаление департамента у пользователя
    public void removeDepartmentFromUser(SecurityUser user, Integer departmentNumber) {
        // Ищем департамент для пользователя
        userDepartmentRepository
                .findByUser(user)
                .stream()
                .filter(department -> department.getDepartmentNumber().equals(departmentNumber))
                .findFirst()
                .ifPresent(userDepartmentRepository::delete);

    }
}
