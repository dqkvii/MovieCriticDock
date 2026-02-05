package com.aeribmm.filmcritic.Config.AuthenticationConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    void userDetailsService_ShouldReturnUserDetailsService() {
        User testUser = new User();
        testUser.setEmail("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        assertNotNull(userDetailsService);
        assertEquals(testUser, userDetailsService.loadUserByUsername("test@example.com"));
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void userDetailsService_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        assertThrows(UserNotFoundException.class, () -> 
            userDetailsService.loadUserByUsername("nonexistent@example.com"));
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
        AuthenticationProvider authProvider = applicationConfig.authenticationProvider();

        assertNotNull(authProvider);
    }

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = (BCryptPasswordEncoder) applicationConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}