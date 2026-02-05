package com.aeribmm.filmcritic.Model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieDTOTest {

    private MovieDTO movieDTO;
    private final String ID = "tt0123";
    private final String TITLE = "Test Movie";
    private final String POSTER_URL = "http://example.com/poster.jpg";
    private final String PLOT = "Test plot description";
    private final String GENRE = "Action, Drama";
    private final Integer YEAR = 2023;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        movieDTO.setTitle(TITLE);
        movieDTO.setPosterURL(POSTER_URL);
        movieDTO.setPlot(PLOT);
        movieDTO.setGenre(GENRE);
        movieDTO.setYear(YEAR);

        assertEquals(TITLE, movieDTO.getTitle());
        assertEquals(POSTER_URL, movieDTO.getPosterURL());
        assertEquals(PLOT, movieDTO.getPlot());
        assertEquals(GENRE, movieDTO.getGenre());
        assertEquals(YEAR, movieDTO.getYear());

        MovieDTO paramMovieDTO = new MovieDTO(ID,TITLE, POSTER_URL, PLOT, GENRE, YEAR);

        assertNotNull(paramMovieDTO);
        assertEquals(TITLE, paramMovieDTO.getTitle());
        assertEquals(POSTER_URL, paramMovieDTO.getPosterURL());
        assertEquals(PLOT, paramMovieDTO.getPlot());
        assertEquals(GENRE, paramMovieDTO.getGenre());
        assertEquals(YEAR, paramMovieDTO.getYear());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        movieDTO.setTitle(TITLE);
        movieDTO.setPosterURL(POSTER_URL);
        movieDTO.setPlot(PLOT);
        movieDTO.setGenre(GENRE);
        movieDTO.setYear(YEAR);

        String toString = movieDTO.toString();

        assertNotNull(toString);
        assertTrue(toString.contains(TITLE));
        assertTrue(toString.contains(POSTER_URL));
        assertTrue(toString.contains(PLOT));
        assertTrue(toString.contains(GENRE));
        assertTrue(toString.contains(YEAR.toString()));
    }
}