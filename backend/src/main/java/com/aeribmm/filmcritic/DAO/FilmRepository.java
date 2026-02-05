package com.aeribmm.filmcritic.DAO;

import com.aeribmm.filmcritic.Model.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Movie,Integer> {
    @Transactional
    @Procedure(name = "beiuugh9j96baskmwgfk.getMovieByImdbId")
    Movie getMovieByImdbId(@Param("p_imdb_id") String id);

    @Transactional
    @Procedure(name = "beiuugh9j96baskmwgfk.getRandomMovie")
    Movie getRandomMovie();

//    @Query("SELECT m  from Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%',:keyword,'%'))")
//    List<Movie> search(@Param("keyword") String keyword);
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
    @Transactional
    @Procedure(name =  "getFiveLastMovies")
    List<Movie> getFiveLastMovies();
}
