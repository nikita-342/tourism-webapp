package com.tourism.service;

import com.tourism.model.User;
import com.tourism.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        // Проверяем на null
        if (username == null || password == null) {
            return false;
        }

        // Проверяем на пустые строки после trim
        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty()) {
            return false;
        }

        User user = userRepository.findByUsername(trimmedUsername);
        return user != null && user.getPassword().equals(password);
    }

    public boolean register(String username, String password) {
        // Проверяем на null
        if (username == null || password == null) {
            return false;
        }

        // Проверяем на пустые строки после trim
        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty() || password.trim().isEmpty()) {
            return false;
        }

        // Проверяем, не существует ли уже пользователь
        User existingUser = userRepository.findByUsername(trimmedUsername);
        if (existingUser != null) {
            return false;
        }

        // Создаем нового пользователя
        User newUser = new User(trimmedUsername, password);
        userRepository.save(newUser);
        return true;
    }
}