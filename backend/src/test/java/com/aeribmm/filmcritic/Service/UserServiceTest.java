package com.aeribmm.filmcritic.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aeribmm.filmcritic.DAO.FilmRepositoryString;
import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.UserModel.UserDTO;
import com.aeribmm.filmcritic.Model.UserModel.UserProfileDTO;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WatchListRepository watchListRepository;

    @Mock
    private FilmRepositoryString filmRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Movie testMovie;
    private WatchList testWatchList;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");

        testMovie = new Movie();
        testMovie.setImdbId("tt1234567");
        testMovie.setTitle("Test Movie");
        testMovie.setRuntime("120 min");
        testMovie.setRatingImdb("8.5");

        testWatchList = new WatchList();
        testWatchList.setId(1);
        testWatchList.setUserId(1);
        testWatchList.setMovieId("tt1234567");
        testWatchList.setStatus(WatchListStatus.completed);
        testWatchList.setCreateAt(LocalDate.now());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
        verify(userRepository).findById(1);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
        verify(userRepository).findById(1);
    }

    @Test
    void convertToDTO_ShouldReturnUserDTO() {
        UserDTO result = userService.convertToDTO(testUser);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void createUser_ShouldSaveUser() {
        userService.createUser(testUser);

        verify(userRepository).save(testUser);
    }

    @Test
    void getAll_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
        verify(userRepository).findAll();
    }

    @Test
    void deleteById_ShouldDeleteUser() {
        userService.deleteById(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    void getUserProfile_ShouldReturnUserProfile() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(watchListRepository.findByUserIdAndStatusNot(anyInt(), any(WatchListStatus.class)))
                .thenReturn(Arrays.asList(testWatchList));
        
        lenient().when(filmRepository.findById(anyString())).thenReturn(Optional.of(testMovie));

        UserProfileDTO result = userService.getUserProfile("testUser");

        assertNotNull(result);
        assertEquals(testUser.getName(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(1, result.getTotalViewed());
        verify(userRepository).findByEmail("testUser");
    }

    @Test
    void getUserProfile_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserProfile("nonExistingUser"));
        verify(userRepository).findByEmail("nonExistingUser");
    }
}