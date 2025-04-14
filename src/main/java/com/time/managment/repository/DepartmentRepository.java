package com.time.managment.repository;

import com.time.managment.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findAllByUserTimeSheet_TimeSheet(Integer timeSheet);
}
