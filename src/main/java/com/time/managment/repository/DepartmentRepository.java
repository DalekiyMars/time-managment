package com.time.managment.repository;

import com.time.managment.entity.Department;
import com.time.managment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findAllByUserTimeSheet_TimeSheet(Integer timeSheet);
    Optional<Department> findByUserTimeSheetAndDepartment(User user, Integer department);

}
