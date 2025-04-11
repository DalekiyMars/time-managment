package com.time.managment.repository;

import com.time.managment.entity.Presence;
import com.time.managment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Integer> {
    List<Presence> getPresencesByUserTimeSheet(User userTimeSheet);
    List<Presence> findByUserTimeSheet_TimeSheetAndTimeMarkBetween(Integer timeSheet, LocalDateTime start, LocalDateTime end);

}
