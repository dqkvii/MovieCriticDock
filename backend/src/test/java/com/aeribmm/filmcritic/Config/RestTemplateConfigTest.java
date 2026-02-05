package com.aeribmm.filmcritic.Config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RestTemplateConfigTest {

    @InjectMocks
    private RestTemplateConfig restTemplateConfig;

    @Test
    void restTemplate_ShouldReturnRestTemplate() {
        RestTemplate restTemplate = restTemplateConfig.restTemplate();

        assertNotNull(restTemplate);
    }
}