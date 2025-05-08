package com.example.test_task.controller;

import com.example.test_task.entity.Subscription;
import com.example.test_task.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<Subscription> addSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        return ResponseEntity.ok(subscriptionService.addSubscription(id, subscription));
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<Subscription>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long subId) {
        subscriptionService.deleteSubscription(subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<String>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions());
    }
}