package com.time.managment.repository;

import com.time.managment.entity.User;
import com.time.managment.entity.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    List<Weekend> getWeekendsByUserTimeSheet(User userTimeSheet);
}
