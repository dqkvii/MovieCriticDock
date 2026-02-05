package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;
import com.aeribmm.filmcritic.Service.MovieService;
import com.aeribmm.filmcritic.Service.OmdbService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    private final MovieService service;
    private final OmdbService omdbService;

    public MovieController(MovieService service,OmdbService oService) {
        this.service = service;
        this.omdbService = oService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id){
        Movie movie = service.getMovie(id);
        if(movie == null){
            throw new MovieNotFoundException();
        }
        return ResponseEntity.ok(movie);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<MovieResponse> getMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(omdbService.getMovieByTitle(title));
    }
    @GetMapping("/randomm")//method is not using
    public ResponseEntity<MovieResponse> randomMovieFromAPI(){
        return ResponseEntity.ok(omdbService.getRandomMovie());
    }
    @GetMapping("/random")
    public ResponseEntity<Movie> getRandomMovie(){
        return ResponseEntity.ok(service.random());
    }
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies(@RequestParam(defaultValue = "40") int limit){
        return ResponseEntity.ok(service.getAll(limit));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(String keyword){
        List<Movie> result = service.search(keyword);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-5")
    public ResponseEntity<List<MovieDTO>> get5(){
        return ResponseEntity.ok(service.get5());
    }
}
