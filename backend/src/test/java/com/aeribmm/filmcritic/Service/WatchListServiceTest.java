package com.aeribmm.filmcritic.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;

@ExtendWith(MockitoExtension.class)
public class WatchListServiceTest {

    @Mock
    private WatchListRepository watchListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WatchListService watchListService;

    private User testUser;
    private WatchList testWatchList;
    private WatchListRequest testRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");

        testWatchList = new WatchList();
        testWatchList.setId(1);
        testWatchList.setUserId(1);
        testWatchList.setMovieId("tt1234567");
        testWatchList.setStatus(WatchListStatus.planned);
        testWatchList.setCreateAt(LocalDate.now());

        testRequest = new WatchListRequest();
        testRequest.setUserId(1);
        testRequest.setMovieId("tt1234567");
        testRequest.setStatus(WatchListStatus.watching);
    }

    @Test
    void getWatchlistByUserId_ShouldReturnWatchlist() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(watchListRepository.getWatchlistByUserId(anyInt())).thenReturn(Arrays.asList(testWatchList));

        List<WatchList> result = watchListService.getWatchlistByUserId(1);

        assertEquals(1, result.size());
        assertEquals(testWatchList, result.get(0));
        verify(userRepository).findById(1);
        verify(watchListRepository).getWatchlistByUserId(1);
    }

    @Test
    void getWatchlistByUserId_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> watchListService.getWatchlistByUserId(1));
        verify(userRepository).findById(1);
    }

    @Test
    void getAll_ShouldReturnAllWatchlists() {
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(testWatchList));

        List<WatchList> result = watchListService.getAll();

        assertEquals(1, result.size());
        assertEquals(testWatchList, result.get(0));
        verify(watchListRepository).findAll();
    }

    @Test
    void addMovieToWatchList_ShouldUpdateExistingEntry_WhenAlreadyExists() {
        when(watchListRepository.findByUserIdAndMovieId(anyInt(), any())).thenReturn(testWatchList);

        watchListService.addMovieToWatchList(testRequest);

        assertEquals(WatchListStatus.watching, testWatchList.getStatus());
        verify(watchListRepository).findByUserIdAndMovieId(1, "tt1234567");
        verify(watchListRepository).save(testWatchList);
    }

    @Test
    void addMovieToWatchList_ShouldCreateNewEntry_WhenDoesNotExist() {
        when(watchListRepository.findByUserIdAndMovieId(anyInt(), any())).thenReturn(null);

        watchListService.addMovieToWatchList(testRequest);

        verify(watchListRepository).findByUserIdAndMovieId(1, "tt1234567");
        verify(watchListRepository).save(any(WatchList.class));
    }
}