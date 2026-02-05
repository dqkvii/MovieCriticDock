package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Service.DeepSeekService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class DeepseekController {

    private final DeepSeekService deepSeekService;

    public DeepseekController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/messages")
    public ResponseEntity<String> createAiMessage(@RequestBody String prompt) {
        try {
            String response = deepSeekService.generateText(prompt);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing request: " + e.getMessage());
        }
    }
}