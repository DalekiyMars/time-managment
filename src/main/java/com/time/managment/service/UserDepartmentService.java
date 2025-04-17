package com.time.managment.service;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.repository.SecurityUserRepository;
import com.time.managment.repository.UserDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (userDepartmentRepository.existsByUser_TimesheetAndDepartmentNumber(user.getTimesheet(), departmentNumber))
            throw new SomethingWentWrong("У пользователя уже есть этот отдел!");
        // Убедимся, что у пользователя правильно установлен timesheet
        if (user.getTimesheet() == null) {
            throw new IllegalArgumentException("User timesheet cannot be null.");
        }

        // Создаем объект UserDepartment с правильным timesheet
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setUser(securityUserRepository.findByTimesheet(user.getTimesheet()));  // указываем пользователя
        userDepartment.setDepartmentNumber(departmentNumber); // указываем номер департамента

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
                .findFirst().ifPresent(userDepartmentRepository::delete);

    }
}
