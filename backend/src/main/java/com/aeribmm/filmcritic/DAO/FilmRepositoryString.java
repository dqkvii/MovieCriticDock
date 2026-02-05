package com.aeribmm.filmcritic.DAO;

import com.aeribmm.filmcritic.Model.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepositoryString extends JpaRepository<Movie,String> {
}
