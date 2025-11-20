package com.tourism.service;

import com.tourism.model.User;
import com.tourism.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginShouldSucceedWithCorrectCredentials() {
        // Arrange
        User mockUser = new User("admin", "1234");
        when(userRepository.findByUsername("admin")).thenReturn(mockUser);

        // Act
        boolean result = authService.login("admin", "1234");

        // Assert
        assertTrue(result);
        verify(userRepository).findByUsername("admin");
    }

    @Test
    void loginShouldFailWithWrongPassword() {
        // Arrange
        User mockUser = new User("admin", "1234");
        when(userRepository.findByUsername("admin")).thenReturn(mockUser);

        // Act
        boolean result = authService.login("admin", "wrong");

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("admin");
    }

    @Test
    void loginShouldFailIfUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("ghost")).thenReturn(null);

        // Act
        boolean result = authService.login("ghost", "1234");

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("ghost");
    }

    @Test
    void loginShouldFailWithNullUsername() {
        // Act & Assert
        assertFalse(authService.login(null, "1234"));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void loginShouldFailWithNullPassword() {
        // Act & Assert
        assertFalse(authService.login("admin", null));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void loginShouldFailWithEmptyUsername() {
        // Act & Assert
        assertFalse(authService.login("", "1234"));
        assertFalse(authService.login("   ", "1234"));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void loginShouldWorkWithEmptyPasswordIfStoredPasswordIsEmpty() {
        // Arrange
        User mockUser = new User("emptyuser", "");
        when(userRepository.findByUsername("emptyuser")).thenReturn(mockUser);

        // Act & Assert
        assertTrue(authService.login("emptyuser", ""));
        verify(userRepository).findByUsername("emptyuser");
    }

    @Test
    void loginShouldTrimUsername() {
        // Arrange
        User mockUser = new User("admin", "1234");
        when(userRepository.findByUsername("admin")).thenReturn(mockUser);

        // Act
        boolean result = authService.login("  admin  ", "1234");

        // Assert
        assertTrue(result);
        verify(userRepository).findByUsername("admin");
    }

    @Test
    void registerShouldSucceedWithNewUser() {
        // Arrange
        when(userRepository.findByUsername("newuser")).thenReturn(null);

        // Act
        boolean result = authService.register("newuser", "password");

        // Assert
        assertTrue(result);
        verify(userRepository).findByUsername("newuser");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerShouldFailWithExistingUser() {
        // Arrange
        User existingUser = new User("existing", "pass");
        when(userRepository.findByUsername("existing")).thenReturn(existingUser);

        // Act
        boolean result = authService.register("existing", "newpass");

        // Assert
        assertFalse(result);
        verify(userRepository).findByUsername("existing");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerShouldFailWithEmptyUsername() {
        // Act & Assert
        assertFalse(authService.register("", "pass"));
        assertFalse(authService.register("   ", "pass"));
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerShouldFailWithEmptyPassword() {
        // Act & Assert
        assertFalse(authService.register("user", ""));
        assertFalse(authService.register("user", "   "));
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerShouldFailWithNullCredentials() {
        // Act & Assert
        assertFalse(authService.register(null, "pass"));
        assertFalse(authService.register("user", null));
        assertFalse(authService.register(null, null));
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}