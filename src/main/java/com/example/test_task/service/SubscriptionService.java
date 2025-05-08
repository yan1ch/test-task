package com.example.test_task.service;

import com.example.test_task.entity.Subscription;
import com.example.test_task.entity.User;
import com.example.test_task.repository.SubscriptionRepository;
import com.example.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription addSubscription(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId).orElseThrow();
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public void deleteSubscription(Long subId) {
        subscriptionRepository.deleteById(subId);
    }

    public List<String> getTopSubscriptions() {
        return subscriptionRepository.findTopServiceNames(PageRequest.of(0, 3));
    }
}
