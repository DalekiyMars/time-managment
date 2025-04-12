package com.time.managment.repository;

import com.time.managment.entity.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    List<Weekend> findByUserTimeSheet(Integer userTimeSheet);  // Поиск по timesheet типа Integer

    List<Weekend> findByUserTimeSheetAndWeekendDateBetween(Integer userTimeSheet, LocalDate start, LocalDate end);

    void deleteByUserTimeSheetAndWeekendDate(Integer userTimeSheet, LocalDate weekendDate);
}
