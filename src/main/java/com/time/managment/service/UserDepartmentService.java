package com.time.managment.service;

import com.time.managment.dto.HandlerDto;
import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.repository.SecurityUserRepository;
import com.time.managment.repository.UserDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public HandlerDto addDepartmentToUserForView(SecurityUser user, Integer departmentNumber) {
        try {
            if (userDepartmentRepository.existsByUser_TimesheetAndDepartmentNumber(user.getTimesheet(), departmentNumber)) {
                return new HandlerDto()
                        .setSuccess(false)
                        .setMessage("У пользователя уже есть этот отдел!");
            }

            if (user.getTimesheet() == null) {
                return new HandlerDto()
                        .setSuccess(false)
                        .setMessage("Табельный номер не может быть пустым.");
            }

            final SecurityUser fullUser = securityUserRepository.findByTimesheet(user.getTimesheet());

            final UserDepartment userDepartment = new UserDepartment()
                    .setUser(fullUser)
                    .setDepartmentNumber(departmentNumber);

            userDepartmentRepository.save(userDepartment);

            return new HandlerDto()
                    .setSuccess(true)
                    .setMessage("Департамент успешно добавлен!");
        } catch (Exception e) {
            return new HandlerDto()
                    .setSuccess(false)
                    .setMessage("Ошибка добавления департамента: " + e.getMessage());
        }
    }

    // Удаление департамента у пользователя
    public void removeDepartmentFromUserForREST(SecurityUser user, Integer departmentNumber) {
        // Ищем департамент для пользователя
        userDepartmentRepository
                .findByUser(user)
                .stream()
                .filter(department -> department.getDepartmentNumber().equals(departmentNumber))
                .findFirst()
                .ifPresent(userDepartmentRepository::delete);

    }

    public HandlerDto removeDepartmentFromUserForView(SecurityUser user, Integer departmentNumber) {
        try {
            Optional<UserDepartment> toDelete = userDepartmentRepository
                    .findByUser(user)
                    .stream()
                    .filter(department -> department.getDepartmentNumber().equals(departmentNumber))
                    .findFirst();

            if (toDelete.isEmpty()) {
                return new HandlerDto()
                        .setSuccess(false)
                        .setMessage("Департамент не найден у пользователя.");
            }

            userDepartmentRepository.delete(toDelete.get());

            return new HandlerDto()
                    .setSuccess(true)
                    .setMessage("Департамент успешно удалён.");
        } catch (Exception e) {
            return new HandlerDto()
                    .setSuccess(false)
                    .setMessage("Ошибка удаления департамента: " + e.getMessage());
        }
    }
}
