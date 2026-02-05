package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final FilmRepository repository;


    public MovieService(FilmRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Movie getMovie(String id) {
        return repository.getMovieByImdbId(id);
    }
    @Transactional
    public Movie random(){
        return repository.getRandomMovie();
    }

    public List<MovieDTO> getAll(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        return repository.findAll(pageable)
                .map(movie -> new MovieDTO(movie.getImdbId(), movie.getTitle(), movie.getPoster(),movie.getPlot(),movie.getGenre(),movie.getYear()))
                .getContent();
    }

    public List<Movie> search(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }

    @Transactional
    public List<MovieDTO> get5(){
        List<Movie> list = repository.getFiveLastMovies();
        List<MovieDTO> result = new ArrayList<>();
        for(Movie movie : list){
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setPosterURL(movie.getPoster());
            movieDTO.setYear(movie.getYear());
            movieDTO.setGenre(movie.getGenre());
            movieDTO.setPlot(movie.getPlot());
            result.add(movieDTO);
        }
        return result;
    }
}
