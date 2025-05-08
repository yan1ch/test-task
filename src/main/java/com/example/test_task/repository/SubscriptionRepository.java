package com.example.test_task.repository;

import com.example.test_task.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Query("SELECT s.serviceName FROM Subscription s GROUP BY s.serviceName ORDER BY COUNT(s.serviceName) DESC")
    List<String> findTopServiceNames(org.springframework.data.domain.Pageable pageable);
}
