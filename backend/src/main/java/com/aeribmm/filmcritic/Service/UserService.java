package com.aeribmm.filmcritic.Service;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.DAO.FilmRepositoryString;
import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import com.aeribmm.filmcritic.Model.Movie.MovieProfile;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.UserModel.UserDTO;
import com.aeribmm.filmcritic.Model.UserModel.UserProfileDTO;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;
    private final WatchListRepository watchListRepository;
    private final FilmRepositoryString filmRepository;

    public UserService(UserRepository repository,WatchListRepository repo,FilmRepositoryString repository1) {
        this.repository = repository;
        this.watchListRepository = repo;
        this.filmRepository = repository1;
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(),user.getName(),user.getEmail());
    }

    public void createUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public UserProfileDTO getUserProfile(String email) {
        System.out.println(email);
        User user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        List<WatchList> viewed = watchListRepository.findByUserIdAndStatusNot(user.getId(), WatchListStatus.planned);
        List<WatchList> all = watchListRepository.findByUserId(user.getId());
        List<MovieProfile> movies = new ArrayList<>();

        for(WatchList item : viewed){
            String id = item.getMovieId();
            Movie film = filmRepository.findById(id).orElse(null);
            if(film != null){
                MovieProfile movie = new MovieProfile();
                movie.setId(film.getImdbId());
                movie.setTitle(film.getTitle());
                movie.setPosterURL(film.getPoster());
                movie.setPlot(film.getPlot());
                movie.setGenre(film.getGenre());
                movie.setStatus(item.getStatus());
                movies.add(movie);
            }

        }
        int totalViewed = viewed.size();
        double avgScore = calculateAvgScore(viewed);
        String timeSpend = calculateTimeSpend(viewed);
        return new UserProfileDTO(user.getId(),user.getName(),user.getEmail(),totalViewed,avgScore,timeSpend,movies);
    }


    private double calculateAvgScore(List<WatchList> viewed) {
        if (viewed == null || viewed.isEmpty()) {
            return 0.0;
        }
        List<Double> ratings = viewed.stream()
                .map(item -> filmRepository.findById(item.getMovieId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Movie::getRatingImdb)
                .filter(Objects::nonNull)
                .map(rating -> {
                    try {
                        return Double.parseDouble(rating);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (ratings.isEmpty()) {
            return 0.0;
        }


        double sum = ratings.stream().mapToDouble(Double::doubleValue).sum();
        return Math.round((sum / ratings.size()) * 10.0) / 10.0;
    }
    private String calculateTimeSpend(List<WatchList> viewed){
        if(viewed == null || viewed.isEmpty()){
            return "0 m";
        }

        int totalMinutes = viewed.stream()
                .map(item -> filmRepository.findById(item.getMovieId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Movie::getRuntime)
                .filter(Objects::nonNull)
                .map(runtime -> {
                    try {
                        return Integer.parseInt(runtime.replaceAll("\\D+", "")); // Извлекаем цифры из "142 min"
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .reduce(0, Integer::sum);

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        return hours + "h " + minutes + "m";
    }
}
