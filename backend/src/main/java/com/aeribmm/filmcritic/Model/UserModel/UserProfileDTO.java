package com.aeribmm.filmcritic.Model.UserModel;

import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import com.aeribmm.filmcritic.Model.Movie.MovieProfile;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;

import java.util.List;

public class UserProfileDTO {
    private Integer id;
    private String username;
    private String email;
    private int totalViewed;
    private double averageScore;
    private String timeSpend;
    private List<MovieProfile> list;

    public UserProfileDTO() {
    }

    public UserProfileDTO(Integer id,String username, String email, int totalViewed, double averageScore, String timeSpend, List<MovieProfile> list) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.totalViewed = totalViewed;
        this.averageScore = averageScore;
        this.timeSpend = timeSpend;
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalViewed() {
        return totalViewed;
    }

    public void setTotalViewed(int totalViewed) {
        this.totalViewed = totalViewed;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(String timeSpend) {
        this.timeSpend = timeSpend;
    }

    public List<MovieProfile> getList() {
        return list;
    }

    public void setList(List<MovieProfile> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", totalViewed=" + totalViewed +
                ", averageScore=" + averageScore +
                ", timeSpend='" + timeSpend + '\'' +
                ", list=" + list +
                '}';
    }
}
