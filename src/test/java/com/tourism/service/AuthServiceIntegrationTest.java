package com.tourism.service;

import com.tourism.repository.StubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceIntegrationTest {

    private AuthService authService;
    private StubUserRepository stubUserRepository;

    @BeforeEach
    void setUp() {
        stubUserRepository = new StubUserRepository();
        authService = new AuthService(stubUserRepository);
    }

    @Test
    void shouldLoginSuccessfullyWithStubRepository() {
        // Act & Assert
        assertTrue(authService.login("admin", "1234"));
        assertTrue(authService.login("user1", "password1"));
    }

    @Test
    void shouldLoginWithEmptyPasswordIfStoredPasswordIsEmpty() {
        // Act & Assert
        assertTrue(authService.login("emptyuser", ""));
    }

    @Test
    void shouldFailLoginWithWrongPasswordUsingStub() {
        // Act & Assert
        assertFalse(authService.login("admin", "wrong"));
        assertFalse(authService.login("user1", "incorrect"));
    }

    @Test
    void shouldFailLoginWithNonExistentUser() {
        // Act & Assert
        assertFalse(authService.login("nonexistent", "password"));
        assertFalse(authService.login("ghost", "1234"));
    }

    @Test
    void shouldFailLoginWithNullUsername() {
        assertFalse(authService.login(null, "password"));
    }

    @Test
    void shouldFailLoginWithNullPassword() {
        assertFalse(authService.login("admin", null));
    }

    @Test
    void shouldFailLoginWithEmptyUsername() {
        assertFalse(authService.login("", "password"));
        assertFalse(authService.login("   ", "password"));
    }

    @Test
    void integrationTest_LoginAfterRegistration() {
        // Act - регистрируем нового пользователя
        boolean registrationResult = authService.register("testuser", "testpass");

        // Assert - проверяем что регистрация успешна
        assertTrue(registrationResult);

        // Act - пытаемся войти с новыми credentials
        boolean loginResult = authService.login("testuser", "testpass");

        // Assert - проверяем что вход успешен
        assertTrue(loginResult);
    }

    @Test
    void integrationTest_RegisterWithExistingUserShouldFail() {
        // Act & Assert - попытка зарегистрировать существующего пользователя
        assertFalse(authService.register("admin", "newpassword"));

        // Проверяем что пароль не изменился
        assertTrue(authService.login("admin", "1234"));
        assertFalse(authService.login("admin", "newpassword"));
    }
}