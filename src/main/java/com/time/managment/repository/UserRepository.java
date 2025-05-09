package com.time.managment.repository;

import com.time.managment.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTimeSheet(@NotNull Integer timeSheet);
    void deleteByTimeSheet(@NotNull Integer timeSheet);
    boolean existsByTimeSheet(@NotNull Integer timeSheet);}
