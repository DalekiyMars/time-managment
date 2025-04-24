package com.time.managment.repository;

import com.time.managment.entity.SecurityUser;
import com.time.managment.entity.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Integer> {
    // Найти департаменты по пользователю
    List<UserDepartment> findByUser(SecurityUser user);
    boolean existsByUser_TimesheetAndDepartmentNumber(Integer user_timesheet, Integer departmentNumber);

}
