package com.aeribmm.filmcritic.Model.WatchListModel;

import jakarta.persistence.*;


import java.time.LocalDate;


@Entity
@Table(name = "watchlist")
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "movie_id", nullable = false)
    private String movieId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WatchListStatus status;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createAt;

    public WatchList() {
    }
    public WatchList(Integer id, Integer userId, String movieId, WatchListStatus status, LocalDate createAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.status = status;
        this.createAt = createAt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public WatchListStatus getStatus() {
        return status;
    }

    public void setStatus(WatchListStatus status) {
        this.status = status;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "WatchList{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId='" + movieId + '\'' +
                ", status=" + status +
                ", createAt=" + createAt +
                '}';
    }
}
