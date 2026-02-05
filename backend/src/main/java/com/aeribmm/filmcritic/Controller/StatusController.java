package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import com.aeribmm.filmcritic.Service.WatchListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("status")
public class StatusController {
    private final WatchListService service;

    public StatusController(WatchListService service) {
        this.service = service;
    }

    @PostMapping("/add")//status 201(created)
    public ResponseEntity<String> addToWatchList(@RequestBody WatchListRequest request) {
        service.addMovieToWatchList(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success.");
    }
}
