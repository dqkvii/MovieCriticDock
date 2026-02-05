package com.aeribmm.filmcritic.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;
import com.aeribmm.filmcritic.Service.WatchListService;

@ExtendWith(MockitoExtension.class)
public class StatusControllerTest {

    @Mock
    private WatchListService watchListService;

    @InjectMocks
    private StatusController statusController;

    private WatchListRequest testRequest;

    @BeforeEach
    void setUp() {
        testRequest = new WatchListRequest();
        testRequest.setUserId(1);
        testRequest.setMovieId("tt1234567");
        testRequest.setStatus(WatchListStatus.watching);
    }

    @Test
    void addToWatchList_ShouldAddMovieToWatchList() {
        ResponseEntity<String> response = statusController.addToWatchList(testRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success.", response.getBody());
        verify(watchListService).addMovieToWatchList(testRequest);
    }
}