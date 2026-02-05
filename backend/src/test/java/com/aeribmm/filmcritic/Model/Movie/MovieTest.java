package com.aeribmm.filmcritic.Model.Movie;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieTest {

    private Movie movie;
    private final String IMDB_ID = "tt1234567";
    private final String TITLE = "Test Movie";
    private final Integer YEAR = 2023;
    private final LocalDate RELEASED = LocalDate.of(2023, 1, 1);
    private final String RUNTIME = "120 min";
    private final String DIRECTOR = "Test Director";
    private final String PLOT = "Test plot description";
    private final String COUNTRY = "USA";
    private final String POSTER = "http://example.com/poster.jpg";
    private final String RATING_METASCORE = "85";
    private final String RATING_ROT_TOM = "90%";
    private final String RATING_IMDB = "8.5";
    private final String TYPE = "movie";
    private final String GENRE = "Action, Drama";

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        movie.setImdbId(IMDB_ID);
        movie.setTitle(TITLE);
        movie.setYear(YEAR);
        movie.setReleased(RELEASED);
        movie.setRuntime(RUNTIME);
        movie.setDirector(DIRECTOR);
        movie.setPlot(PLOT);
        movie.setCountry(COUNTRY);
        movie.setPoster(POSTER);
        movie.setRatingMetascore(RATING_METASCORE);
        movie.setRatingRotTom(RATING_ROT_TOM);
        movie.setRatingImdb(RATING_IMDB);
        movie.setType(TYPE);
        movie.setGenre(GENRE);

        assertEquals(IMDB_ID, movie.getImdbId());
        assertEquals(TITLE, movie.getTitle());
        assertEquals(YEAR, movie.getYear());
        assertEquals(RELEASED, movie.getReleased());
        assertEquals(RUNTIME, movie.getRuntime());
        assertEquals(DIRECTOR, movie.getDirector());
        assertEquals(PLOT, movie.getPlot());
        assertEquals(COUNTRY, movie.getCountry());
        assertEquals(POSTER, movie.getPoster());
        assertEquals(RATING_METASCORE, movie.getRatingMetascore());
        assertEquals(RATING_ROT_TOM, movie.getRatingRotTom());
        assertEquals(RATING_IMDB, movie.getRatingImdb());
        assertEquals(TYPE, movie.getType());
        assertEquals(GENRE, movie.getGenre());

        Movie paramMovie = new Movie(
            IMDB_ID, TITLE, YEAR, RELEASED, RUNTIME, DIRECTOR, PLOT, COUNTRY,
            POSTER, RATING_METASCORE, RATING_ROT_TOM, RATING_IMDB, TYPE, GENRE
        );

        assertNotNull(paramMovie);
        assertEquals(IMDB_ID, paramMovie.getImdbId());
        assertEquals(TITLE, paramMovie.getTitle());
        assertEquals(YEAR, paramMovie.getYear());
        assertEquals(RELEASED, paramMovie.getReleased());
        assertEquals(RUNTIME, paramMovie.getRuntime());
        assertEquals(DIRECTOR, paramMovie.getDirector());
        assertEquals(PLOT, paramMovie.getPlot());
        assertEquals(COUNTRY, paramMovie.getCountry());
        assertEquals(POSTER, paramMovie.getPoster());
        assertEquals(RATING_METASCORE, paramMovie.getRatingMetascore());
        assertEquals(RATING_ROT_TOM, paramMovie.getRatingRotTom());
        assertEquals(RATING_IMDB, paramMovie.getRatingImdb());
        assertEquals(TYPE, paramMovie.getType());
        assertEquals(GENRE, paramMovie.getGenre());
    }
}