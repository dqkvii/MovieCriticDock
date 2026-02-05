package com.aeribmm.filmcritic.Model.WatchListModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WatchListRequestTest {

    private WatchListRequest request;
    private final Integer USER_ID = 1;
    private final String MOVIE_ID = "tt1234567";
    private final WatchListStatus STATUS = WatchListStatus.watching;

    @BeforeEach
    void setUp() {
        request = new WatchListRequest();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        request.setUserId(USER_ID);
        request.setMovieId(MOVIE_ID);
        request.setStatus(STATUS);

        assertEquals(USER_ID, request.getUserId());
        assertEquals(MOVIE_ID, request.getMovieId());
        assertEquals(STATUS, request.getStatus());

        WatchListRequest paramRequest = new WatchListRequest(USER_ID, MOVIE_ID, STATUS);

        assertNotNull(paramRequest);
        assertEquals(USER_ID, paramRequest.getUserId());
        assertEquals(MOVIE_ID, paramRequest.getMovieId());
        assertEquals(STATUS, paramRequest.getStatus());
    }
}