package com.example.test_task.service;

import com.example.test_task.dto.SubscriptionDTO;
import com.example.test_task.dto.UserDTO;
import com.example.test_task.entity.User;
import com.example.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO userDto) {
        log.info("Создаём нового пользователя с именем: {} и email: {}", userDto.getName(), userDto.getEmail());
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = userRepository.save(user);
        log.info("Пользователь успешно создан с id: {}", savedUser.getId());
        return toDto(savedUser);
    }

    public Optional<UserDTO> getUser(Long id) {
        log.info("Запрос на получение пользователя с id: {}", id);
        return userRepository.findById(id).map(this::toDto);
    }

    public UserDTO updateUser(Long id, UserDTO updatedDto) {
        log.info("Запрос на обновление пользователя с id: {}", id);
        return userRepository.findById(id).map(user -> {
            user.setName(updatedDto.getName());
            user.setEmail(updatedDto.getEmail());
            User updatedUser = userRepository.save(user);
            log.info("Пользователь с id: {} успешно обновлён", updatedUser.getId());
            return toDto(updatedUser);
        }).orElseThrow(() -> {
            log.warn("Пользователь с id: {} не найден", id);
            return new RuntimeException("Пользователь не найден");
        });
    }

    public void deleteUser(Long id) {
        log.info("Запрос на удаление пользователя с id: {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь с id: {} успешно удалён", id);
    }

    private UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setSubscriptions(
                user.getSubscriptions().stream().map(sub -> {
                    SubscriptionDTO subDto = new SubscriptionDTO();
                    subDto.setId(sub.getId());
                    subDto.setServiceName(sub.getServiceName());
                    return subDto;
                }).collect(Collectors.toList())
        );
        return dto;
    }
}
