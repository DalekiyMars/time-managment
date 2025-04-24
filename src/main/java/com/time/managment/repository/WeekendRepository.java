package com.time.managment.repository;

import com.time.managment.constants.AbsenceReason;
import com.time.managment.entity.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    List<Weekend> findByUserTimeSheet(Integer userTimeSheet);  // Поиск по timesheet типа Integer

    List<Weekend> findByUserTimeSheetAndWeekendDateBetween(Integer userTimeSheet, LocalDate start, LocalDate end);

    Optional<Weekend> findByUserTimeSheetAndWeekendDate(Integer userTimeSheet, LocalDate weekendDate);

    boolean existsByUserTimeSheetAndWeekendDateAndStartTimeAndEndTimeAndReason(
            Integer userTimeSheet, LocalDate weekendDate,
            LocalTime startTime, LocalTime endTime, AbsenceReason reason
    );
}
