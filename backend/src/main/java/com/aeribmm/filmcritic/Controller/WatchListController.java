package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Service.WatchListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/watch-list")
@SecurityRequirement(name = "bearerAuth")
public class WatchListController {

    private final WatchListService watchListService;

    public WatchListController(WatchListService service) {
        this.watchListService = service;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WatchList>> getWatchListByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(watchListService.getWatchlistByUserId(userId));
    }
    @GetMapping
    public ResponseEntity<List<WatchList>> getAll(){
        return ResponseEntity.ok(watchListService.getAll());
    }
}
