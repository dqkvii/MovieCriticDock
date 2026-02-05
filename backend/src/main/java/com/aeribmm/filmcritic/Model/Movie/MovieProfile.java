package com.aeribmm.filmcritic.Model.Movie;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;

public class MovieProfile {
    private String id;
    private String title;
    private String plot;
    private String posterURL;
    private String genre;
    private WatchListStatus status;

    public MovieProfile() {
    }

    public MovieProfile(String id,String title, String plot, String posterURL, String genre, WatchListStatus status) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.posterURL = posterURL;
        this.genre = genre;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public WatchListStatus getStatus() {
        return status;
    }

    public void setStatus(WatchListStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MovieProfile{" +
                "title='" + title + '\'' +
                ", plot='" + plot + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", genre='" + genre + '\'' +
                ", status=" + status +
                '}';
    }
}
