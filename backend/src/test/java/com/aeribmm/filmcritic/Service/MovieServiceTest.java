package com.aeribmm.filmcritic.Service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setImdbId("tt1234567");
        testMovie.setTitle("Test Movie");
        testMovie.setYear(2023);
        testMovie.setGenre("Action");
        testMovie.setPlot("Test plot");
        testMovie.setPoster("http://example.com/poster.jpg");
    }

    @Test
    void getMovie_ShouldReturnMovie() {
        when(filmRepository.getMovieByImdbId(anyString())).thenReturn(testMovie);

        Movie result = movieService.getMovie("tt1234567");

        assertNotNull(result);
        assertEquals(testMovie.getImdbId(), result.getImdbId());
        assertEquals(testMovie.getTitle(), result.getTitle());
        verify(filmRepository).getMovieByImdbId("tt1234567");
    }

    @Test
    void random_ShouldReturnRandomMovie() {
        when(filmRepository.getRandomMovie()).thenReturn(testMovie);

        Movie result = movieService.random();

        assertNotNull(result);
        assertEquals(testMovie, result);
        verify(filmRepository).getRandomMovie();
    }

    @Test
    void getAll_ShouldReturnMovieDTOs() {
        List<Movie> movies = Arrays.asList(testMovie, testMovie);
        Page<Movie> moviesPage = new PageImpl<>(movies);
        when(filmRepository.findAll(any(Pageable.class))).thenReturn(moviesPage);

        List<MovieDTO> result = movieService.getAll(10);

        assertEquals(2, result.size());
        assertEquals(testMovie.getTitle(), result.get(0).getTitle());
        assertEquals(testMovie.getPoster(), result.get(0).getPosterURL());
        verify(filmRepository).findAll(any(Pageable.class));
    }

    @Test
    void search_ShouldReturnMatchingMovies() {
        List<Movie> movies = Arrays.asList(testMovie);
        when(filmRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(movies);

        List<Movie> result = movieService.search("Test");

        assertEquals(1, result.size());
        assertEquals(testMovie, result.get(0));
        verify(filmRepository).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    void get5_ShouldReturnFiveMovieDTOs() {
        List<Movie> movies = Arrays.asList(testMovie, testMovie, testMovie, testMovie, testMovie);
        when(filmRepository.getFiveLastMovies()).thenReturn(movies);

        List<MovieDTO> result = movieService.get5();

        assertEquals(5, result.size());
        for (MovieDTO dto : result) {
            assertEquals(testMovie.getTitle(), dto.getTitle());
            assertEquals(testMovie.getPoster(), dto.getPosterURL());
        }
        verify(filmRepository).getFiveLastMovies();
    }
}