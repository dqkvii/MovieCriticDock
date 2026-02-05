package com.aeribmm.filmcritic.Controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.UserModel.UserDTO;
import com.aeribmm.filmcritic.Model.UserModel.UserProfileDTO;
import com.aeribmm.filmcritic.Service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private UserDTO testUserDTO;
    private UserProfileDTO testUserProfileDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        testUserDTO = new UserDTO(1, "testUser", "test@example.com");

        testUserProfileDTO = new UserProfileDTO();
        testUserProfileDTO.setUsername("testUser");
        testUserProfileDTO.setEmail("test@example.com");
        testUserProfileDTO.setTotalViewed(10);
        testUserProfileDTO.setAverageScore(8.5);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userService.getUserById(anyInt())).thenReturn(testUser);
        when(userService.convertToDTO(any(User.class))).thenReturn(testUserDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUserDTO, response.getBody());
        verify(userService).getUserById(1);
        verify(userService).convertToDTO(testUser);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(testUser, response.getBody().get(0));
        verify(userService).getAll();
    }

    @Test
    void deleteUserById_ShouldDeleteUser_WhenUserExists() {
        when(userService.getUserById(anyInt())).thenReturn(testUser);

        ResponseEntity<Void> response = userController.deleteUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).getUserById(1);
        verify(userService).deleteById(1);
    }

    @Test
    void deleteUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.getUserById(anyInt())).thenReturn(null);

        ResponseEntity<Void> response = userController.deleteUserById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService).getUserById(1);
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        when(userService.getUserById(anyInt())).thenReturn(testUser);

        ResponseEntity<User> response = userController.updateUser(1, testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        verify(userService).getUserById(1);
        verify(userService).createUser(testUser);
    }

    @Test
    void getProfileByUsername_ShouldReturnUserProfile() {
        when(userService.getUserProfile(anyString())).thenReturn(testUserProfileDTO);

        ResponseEntity<UserProfileDTO> response = userController.getProfileByUsername("testUser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUserProfileDTO, response.getBody());
        verify(userService).getUserProfile("testUser");
    }
}