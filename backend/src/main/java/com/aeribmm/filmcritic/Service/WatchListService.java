package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WatchListService {
    private final WatchListRepository watchlistRepository;
    private final UserRepository repository;

    public WatchListService(WatchListRepository watchlistRepository,UserRepository userRepo) {
        this.watchlistRepository = watchlistRepository;
        this.repository = userRepo;
    }
    @Transactional
    public List<WatchList> getWatchlistByUserId(Integer userId) {//status and exception handled
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return watchlistRepository.getWatchlistByUserId(userId);
    }

    public List<WatchList> getAll() {
        return watchlistRepository.findAll();
    }

    public void addMovieToWatchList(WatchListRequest request) {
        WatchList result = watchlistRepository.findByUserIdAndMovieId(request.getUserId(), request.getMovieId());
        System.out.println(result);
        if(result != null){
            result.setStatus(request.getStatus());
            watchlistRepository.save(result);
            return;
        }

        WatchList list = new WatchList();
        list.setMovieId(request.getMovieId());
        list.setUserId(request.getUserId());
        list.setStatus(request.getStatus());
        list.setCreateAt(LocalDate.now());

        watchlistRepository.save(list);
    }
}
