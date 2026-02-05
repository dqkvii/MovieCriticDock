package com.aeribmm.filmcritic.Model.WatchListModel;

public class WatchListRequest {
    private Integer userId;
    private String movieId;
    private WatchListStatus status;

    public WatchListRequest() {
    }

    public WatchListRequest(Integer userId, String movieId, WatchListStatus status) {
        this.userId = userId;
        this.movieId = movieId;
        this.status = status;
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
}
