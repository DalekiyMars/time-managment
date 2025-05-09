package com.time.managment.repository;

import com.time.managment.entity.Presence;
import com.time.managment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Integer> {
    List<Presence> getPresencesByUserTimeSheet(User userTimeSheet);
    @Query("SELECT p FROM Presence p WHERE p.userTimeSheet.timeSheet = :userId AND p.timeMark >= :start AND p.timeMark < :end")
    List<Presence> findByUserIdAndTimeMarkInRange(@Param("userId") Integer userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
