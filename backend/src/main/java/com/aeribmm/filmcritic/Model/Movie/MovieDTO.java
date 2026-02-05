package com.aeribmm.filmcritic.Model.Movie;

public class MovieDTO {
    private String id;
    private String title;
    private String posterURL;
    private String plot;
    private String genre;
    private Integer year;

    public MovieDTO() {
    }

    public MovieDTO(String id,String title, String posterURL, String plot, String genre, Integer year) {
        this.id = id;
        this.title = title;
        this.posterURL = posterURL;
        this.plot = plot;
        this.genre = genre;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", plot='" + plot + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                '}';
    }
}
