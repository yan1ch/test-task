package com.example.test_task.controller;

import com.example.test_task.dto.SubscriptionDTO;
import com.example.test_task.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionDTO> addSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO dto) {
        return ResponseEntity.ok(subscriptionService.addSubscription(id, dto));
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @PathVariable Long subId) {
        subscriptionService.deleteSubscription(subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<String>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions());
    }
}
