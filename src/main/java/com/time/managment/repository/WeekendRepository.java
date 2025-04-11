package com.time.managment.repository;

import com.time.managment.entity.User;
import com.time.managment.entity.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    List<Weekend> findByUserTimeSheet(Integer userTimeSheet);  // Поиск по timesheet типа Integer
    List<Weekend> getWeekendsByUserTimeSheet(Integer userTimeSheet);
    @Query(value = """
    SELECT EXTRACT(MONTH FROM w.weekend_date) AS month, COUNT(*) 
    FROM weekends w 
    JOIN users u ON w.user_timesheet = u.id 
    WHERE u.timesheet = :timeSheet 
      AND w.weekend_date >= :startDate 
    GROUP BY EXTRACT(MONTH FROM w.weekend_date)
    """, nativeQuery = true)
    List<Object[]> countByMonth(@Param("timeSheet") Integer timeSheet, @Param("startDate") LocalDate startDate);
    List<Weekend> findByUserTimeSheetAndWeekendDateBetween(Integer userTimeSheet, LocalDate startDate, LocalDate endDate);

    void deleteByUserTimeSheetAndWeekendDate(Integer userTimeSheet, LocalDate weekendDate);
}
