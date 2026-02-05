package com.aeribmm.filmcritic.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OmdbServiceTest {

    @Mock
    private RestTemplate restTemplate;
    
    @InjectMocks
    private OmdbService omdbService;

    private final String apiKey = "16e531e9";
    private MovieResponse testMovieResponse;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(omdbService, "apiKey", apiKey);
        
        testMovieResponse = new MovieResponse();
        testMovieResponse.setImdbId("tt0032138");
        testMovieResponse.setTitle("The Wizard of Oz");
        testMovieResponse.setYear(1939);
    }

    @Test
    void getMovieByTitle_ShouldReturnMovieResponse() {
        String title = "The Wizard of Oz";
        
        when(restTemplate.getForObject(anyString(), eq(MovieResponse.class))).thenReturn(testMovieResponse);
        
        MovieResponse result = omdbService.getMovieByTitle(title);
        
        assertNotNull(result);
        
        assertEquals(testMovieResponse.getImdbId(), result.getImdbId());
        assertEquals(testMovieResponse.getTitle(), result.getTitle());
        assertEquals(testMovieResponse.getYear(), result.getYear());
    }
}