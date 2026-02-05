package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class OmdbService {

    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public OmdbService(RestTemplate restTemplate,ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;
    }

    @Value("${omdb.api.key}")
    private String apiKey;
    private final String BASE_URL_BY_ID = "http://www.omdbapi.com/?i=%s&apikey=%s" ;

    //"https://www.omdbapi.com/?apikey=%s&i=%s"
    private final String BASE_URL ="https://www.omdbapi.com/?apikey=%s&t=%s";
    public MovieResponse getMovieByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(BASE_URL, apiKey, title.replace(" ", "+"));

        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse getRandomMovie() {
        int maxConcurrentRequests = 10;
        int maxAttempts = 100;

        Random random = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(maxConcurrentRequests);
        List<CompletableFuture<MovieResponse>> futures = new ArrayList<>();

        for (int i = 0; i < maxConcurrentRequests; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                for (int attempt = 0; attempt < maxAttempts / maxConcurrentRequests; attempt++) {
                    int id = 1000000 + random.nextInt(2000000);
                    String imdbId = String.format("tt%07d", id);
                    String url = String.format(BASE_URL_BY_ID, imdbId, apiKey);

                    try {
                        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                        JsonNode root = objectMapper.readTree(response.getBody());
                        JsonNode ratings = root.path("Ratings");

                        if ("True".equalsIgnoreCase(root.path("Response").asText())) {
                            return objectMapper.treeToValue(root, MovieResponse.class);
                        }
                    } catch (Exception e) {
                        System.out.println("Error with ID: " + imdbId);
                    }
                }
                return null;
            }, executor));
        }

        try {
            CompletableFuture<MovieResponse> firstCompleted = CompletableFuture.anyOf(
                            futures.toArray(new CompletableFuture[0]))
                    .thenApply(response -> (MovieResponse) response);

            MovieResponse result = firstCompleted.get(30, TimeUnit.SECONDS);
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        throw new RuntimeException("Failed to find a valid movie after " + maxAttempts + " attempts.");
    }

}
