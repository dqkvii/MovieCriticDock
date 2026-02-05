package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Aunth.AuthenticationRequest;
import com.aeribmm.filmcritic.Aunth.AuthenticationResponse;
import com.aeribmm.filmcritic.Aunth.RegisterRequest;
import com.aeribmm.filmcritic.Service.JWTTokens.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/users/register")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authenticateUser(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
