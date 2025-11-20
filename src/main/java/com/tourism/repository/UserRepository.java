package com.tourism.repository;

import com.tourism.model.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}