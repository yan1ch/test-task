package com.example.test_task.service;

import com.example.test_task.dto.SubscriptionDTO;
import com.example.test_task.entity.Subscription;
import com.example.test_task.entity.User;
import com.example.test_task.repository.SubscriptionRepository;
import com.example.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionDTO addSubscription(Long userId, SubscriptionDTO dto) {
        log.info("Добавляем подписку для пользователя с id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Пользователь с id: {} не найден", userId);
            return new RuntimeException("Пользователь не найден");
        });

        Subscription subscription = new Subscription();
        subscription.setServiceName(dto.getServiceName());
        subscription.setUser(user);

        Subscription saved = subscriptionRepository.save(subscription);
        dto.setId(saved.getId());
        log.info("Подписка на сервис '{}' успешно добавлена для пользователя с id: {}", dto.getServiceName(), userId);
        return dto;
    }

    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        log.info("Получаем все подписки для пользователя с id: {}", userId);
        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(sub -> {
                    SubscriptionDTO dto = new SubscriptionDTO();
                    dto.setId(sub.getId());
                    dto.setServiceName(sub.getServiceName());
                    return dto;
                }).collect(Collectors.toList());
    }

    public void deleteSubscription(Long subId) {
        log.info("Удаляем подписку с id: {}", subId);
        subscriptionRepository.deleteById(subId);
        log.info("Подписка с id: {} успешно удалена", subId);
    }

    public List<String> getTopSubscriptions() {
        log.info("Получаем топ-3 подписок");
        List<String> topSubscriptions = subscriptionRepository.findTopServiceNames(PageRequest.of(0, 3));
        log.info("Топ-3 подписок: {}", topSubscriptions);
        return topSubscriptions;
    }
}
