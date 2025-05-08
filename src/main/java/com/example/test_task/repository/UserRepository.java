package com.example.test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<com.example.test_task.entity.User, Long> {
}
