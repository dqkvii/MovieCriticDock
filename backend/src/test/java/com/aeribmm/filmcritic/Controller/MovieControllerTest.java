package com.aeribmm.filmcritic.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;
import com.aeribmm.filmcritic.Service.MovieService;
import com.aeribmm.filmcritic.Service.OmdbService;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @Mock
    private OmdbService omdbService;

    @InjectMocks
    private MovieController movieController;

    private Movie testMovie;
    private MovieDTO testMovieDTO;
    private MovieResponse testMovieResponse;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setImdbId("tt1234567");
        testMovie.setTitle("Test Movie");
        testMovie.setYear(2023);
        testMovie.setGenre("Action");
        testMovie.setPlot("Test plot");
        testMovie.setPoster("http://example.com/poster.jpg");

        testMovieDTO = new MovieDTO();
        testMovieDTO.setTitle("Test Movie");
        testMovieDTO.setPlot("Test plot");
        testMovieDTO.setPosterURL("http://example.com/poster.jpg");
        testMovieDTO.setGenre("Action");
        testMovieDTO.setYear(2023);

        testMovieResponse = new MovieResponse();
        testMovieResponse.setImdbId("tt1234567");
        testMovieResponse.setTitle("Test Movie from OMDB");
    }

    @Test
    void getMovieById_ShouldReturnMovie_WhenMovieExists() {
        when(movieService.getMovie(anyString())).thenReturn(testMovie);

        ResponseEntity<Movie> response = movieController.getMovieById("tt1234567");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testMovie, response.getBody());
        verify(movieService).getMovie("tt1234567");
    }

    @Test
    void getMovieById_ShouldThrowException_WhenMovieDoesNotExist() {
        when(movieService.getMovie(anyString())).thenReturn(null);

        assertThrows(MovieNotFoundException.class, () -> movieController.getMovieById("nonexistent"));
        verify(movieService).getMovie("nonexistent");
    }

    @Test
    void getMovieByTitle_ShouldReturnMovieResponse() {
        when(omdbService.getMovieByTitle(anyString())).thenReturn(testMovieResponse);

        ResponseEntity<MovieResponse> response = movieController.getMovieByTitle("Test Movie");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testMovieResponse, response.getBody());
        verify(omdbService).getMovieByTitle("Test Movie");
    }

    @Test
    void randomMovieFromAPI_ShouldReturnRandomMovie() {
        when(omdbService.getRandomMovie()).thenReturn(testMovieResponse);

        ResponseEntity<MovieResponse> response = movieController.randomMovieFromAPI();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testMovieResponse, response.getBody());
        verify(omdbService).getRandomMovie();
    }

    @Test
    void getRandomMovie_ShouldReturnRandomMovie() {
        when(movieService.random()).thenReturn(testMovie);

        ResponseEntity<Movie> response = movieController.getRandomMovie();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testMovie, response.getBody());
        verify(movieService).random();
    }

    @Test
    void getAllMovies_ShouldReturnAllMovies() {
        List<MovieDTO> movies = Arrays.asList(testMovieDTO, testMovieDTO);
        when(movieService.getAll(anyInt())).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.getAllMovies(40);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(movieService).getAll(40);
    }

    @Test
    void searchMovies_ShouldReturnMatchingMovies() {
        List<Movie> movies = Arrays.asList(testMovie);
        when(movieService.search(anyString())).thenReturn(movies);

        ResponseEntity<List<Movie>> response = movieController.searchMovies("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(movieService).search("Test");
    }

    @Test
    void searchMovies_ShouldReturn404_WhenNoMoviesFound() {
        when(movieService.search(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Movie>> response = movieController.searchMovies("NonExistent");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(movieService).search("NonExistent");
    }

    @Test
    void get5_ShouldReturn5Movies() {
        List<MovieDTO> movies = Arrays.asList(testMovieDTO, testMovieDTO, testMovieDTO, testMovieDTO, testMovieDTO);
        when(movieService.get5()).thenReturn(movies);

        ResponseEntity<List<MovieDTO>> response = movieController.get5();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().size());
        verify(movieService).get5();
    }
}