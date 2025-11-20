package com.tourism.service;

import com.tourism.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceBoundaryTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(new StubUserRepository());
    }

    @Test
    void loginWithNullCredentials() {
        assertFalse(authService.login(null, "password"));
        assertFalse(authService.login("username", null));
        assertFalse(authService.login(null, null));
    }

    @Test
    void loginWithEmptyUsername() {
        assertFalse(authService.login("", "password"));
        assertFalse(authService.login("   ", "password"));
    }

    @Test
    void loginWithEmptyPasswordForExistingUser() {
        // Для пользователя с пустым паролем в заглушке
        assertTrue(authService.login("emptyuser", ""));
    }

    @Test
    void loginWithEmptyPasswordForRegularUser() {
        // Для обычного пользователя с непустым паролем
        assertFalse(authService.login("admin", ""));
    }

    @Test
    void loginWithVeryLongCredentials() {
        String longUsername = "a".repeat(1000);
        String longPassword = "b".repeat(1000);

        assertFalse(authService.login(longUsername, "password"));
        assertFalse(authService.login("admin", longPassword));
    }

    @Test
    void registerWithEmptyCredentials() {
        assertFalse(authService.register("", "password"));
        assertFalse(authService.register(null, "password"));
        assertFalse(authService.register("username", ""));
        assertFalse(authService.register("username", null));
        assertFalse(authService.register("", ""));
        assertFalse(authService.register(null, null));
    }

    @Test
    void registerWithWhitespaceCredentials() {
        assertFalse(authService.register("  ", "password"));
        assertFalse(authService.register("username", "  "));
        assertFalse(authService.register("  ", "  "));
    }

    @Test
    void loginWithSpecialCharacters() {
        // Проверяем работу со специальными символами
        assertFalse(authService.login("admin!", "1234"));
        assertFalse(authService.login("user@name", "pass"));
        assertFalse(authService.login("user name", "pass word"));
    }

    @Test
    void loginWithUnicodeCharacters() {
        // Тестирование с Unicode символами
        String unicodeUser = "пользователь";
        String unicodePass = "пароль";

        assertFalse(authService.login(unicodeUser, unicodePass));
    }

    @Test
    void registerThenLoginWithSameCredentials() {
        // Регистрируем нового пользователя
        assertTrue(authService.register("newuser", "newpass123"));

        // Логинимся с теми же credentials
        assertTrue(authService.login("newuser", "newpass123"));
    }

    @Test
    void registerWithTrimmedUsername() {
        // Регистрируем с пробелами в имени
        assertTrue(authService.register("  trimmeduser  ", "password"));

        // Логинимся с trim именем
        assertTrue(authService.login("trimmeduser", "password"));
    }
}