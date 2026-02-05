package com.aeribmm.filmcritic.Model.Movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "imdb_id", length = 255, nullable = false, unique = true)
    private String imdbId;

    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @Column(name = "year")
    private Integer year;

    @Column(name = "released")
    private LocalDate released;

    @Column(name = "runtime", length = 255)
    private String runtime;

    @Column(name = "director", length = 256)
    private String director;

    @Column(name = "plot", columnDefinition = "TEXT")
    private String plot;

    @Column(name = "country", length = 256)
    private String country;

    @Column(name = "poster", length = 512)
    private String poster;

    @Column(name = "rating_metascore")
    private String ratingMetascore;

    @Column(name = "rating_rot_tom", length = 4)
    private String ratingRotTom;

    @Column(name = "rating_imdb", precision = 3, scale = 1)
    private String ratingImdb;

    @Column(name = "type", length = 7)
    private String type;
    @Column(name = "genre")
    private String genre;

    public Movie() {}

    public Movie(String imdbId, String title, Integer year, LocalDate released, String runtime, String director, String plot, String country, String poster, String ratingMetascore, String ratingRotTom, String ratingImdb, String type, String genre) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.director = director;
        this.plot = plot;
        this.country = country;
        this.poster = poster;
        this.ratingMetascore = ratingMetascore;
        this.ratingRotTom = ratingRotTom;
        this.ratingImdb = ratingImdb;
        this.type = type;
        this.genre = genre;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRatingMetascore() {
        return ratingMetascore;
    }

    public void setRatingMetascore(String ratingMetascore) {
        this.ratingMetascore = ratingMetascore;
    }

    public String getRatingRotTom() {
        return ratingRotTom;
    }

    public void setRatingRotTom(String ratingRotTom) {
        this.ratingRotTom = ratingRotTom;
    }

    public String getRatingImdb() {
        return ratingImdb;
    }

    public void setRatingImdb(String ratingImdb) {
        this.ratingImdb = ratingImdb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}