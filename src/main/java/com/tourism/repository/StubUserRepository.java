package com.tourism.repository;

import com.tourism.model.User;
import java.util.HashMap;
import java.util.Map;

public class StubUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public StubUserRepository() {
        // Инициализируем тестовыми данными
        initializeUsers();
    }

    private void initializeUsers() {
        users.put("admin", new User("admin", "1234"));
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
        users.put("emptyuser", new User("emptyuser", "")); // пользователь с пустым паролем
        users.put("test", new User("test", "test"));
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    // Вспомогательный метод для тестов
    public int getUserCount() {
        return users.size();
    }

    // Метод для очистки (для тестов)
    public void clear() {
        users.clear();
        initializeUsers();
    }

    // Метод для проверки существования пользователя
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}