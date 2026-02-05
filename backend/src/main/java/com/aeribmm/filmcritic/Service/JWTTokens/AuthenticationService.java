package com.aeribmm.filmcritic.Service.JWTTokens;

import com.aeribmm.filmcritic.Aunth.AuthenticationRequest;
import com.aeribmm.filmcritic.Aunth.AuthenticationResponse;
import com.aeribmm.filmcritic.Aunth.RegisterRequest;
import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Exception.userException.UserAlreadyExistsException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JWTService jwtService;
    public AuthenticationService(UserRepository repo,PasswordEncoder encoder,JWTService service,AuthenticationManager asd) {
        this.repo = repo;
        this.passwordEncoder = encoder;
        this.jwtService = service;
        this.manager = asd;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> user = repo.findByEmail(request.getEmail());
        if(user.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var user1 = User.builder()
                .username(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        System.out.println("name: " + user1.getName() + " email " + user1.getEmail() + " password: " + user1.getPassword());
        repo.createUser(user1.getName(), user1.getEmail(), user1.getPassword());
        var jwtToken = jwtService.generateToken(user1);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
