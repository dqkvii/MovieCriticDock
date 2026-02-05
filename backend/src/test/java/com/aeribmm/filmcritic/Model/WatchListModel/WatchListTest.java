package com.aeribmm.filmcritic.Model.WatchListModel;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WatchListTest {

    private WatchList watchList;
    private final Integer ID = 1;
    private final Integer USER_ID = 1;
    private final String MOVIE_ID = "tt1234567";
    private final WatchListStatus STATUS = WatchListStatus.watching;
    private final LocalDate CREATE_AT = LocalDate.now();

    @BeforeEach
    void setUp() {
        watchList = new WatchList();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        watchList.setId(ID);
        watchList.setUserId(USER_ID);
        watchList.setMovieId(MOVIE_ID);
        watchList.setStatus(STATUS);
        watchList.setCreateAt(CREATE_AT);

        assertEquals(ID, watchList.getId());
        assertEquals(USER_ID, watchList.getUserId());
        assertEquals(MOVIE_ID, watchList.getMovieId());
        assertEquals(STATUS, watchList.getStatus());
        assertEquals(CREATE_AT, watchList.getCreateAt());

        WatchList paramWatchList = new WatchList(ID, USER_ID, MOVIE_ID, STATUS, CREATE_AT);

        assertNotNull(paramWatchList);
        assertEquals(ID, paramWatchList.getId());
        assertEquals(USER_ID, paramWatchList.getUserId());
        assertEquals(MOVIE_ID, paramWatchList.getMovieId());
        assertEquals(STATUS, paramWatchList.getStatus());
        assertEquals(CREATE_AT, paramWatchList.getCreateAt());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        watchList.setId(ID);
        watchList.setUserId(USER_ID);
        watchList.setMovieId(MOVIE_ID);
        watchList.setStatus(STATUS);
        watchList.setCreateAt(CREATE_AT);

        String toString = watchList.toString();

        assertNotNull(toString);
        assertTrue(toString.contains(ID.toString()));
        assertTrue(toString.contains(USER_ID.toString()));
        assertTrue(toString.contains(MOVIE_ID));
        assertTrue(toString.contains(STATUS.toString()));
    }
}