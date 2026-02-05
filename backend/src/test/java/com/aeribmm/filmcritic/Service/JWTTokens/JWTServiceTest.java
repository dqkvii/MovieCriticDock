package com.aeribmm.filmcritic.Service.JWTTokens;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    private UserDetails userDetails;
    private String secretKey;

    @BeforeEach
    void setUp() {
        secretKey = "c7cb75cfb15d4484ff7d823e2800e919081adc904347c90f74855823db20656b5879829ff0364d331ebc43a4576fd65da30773ba847378cebadc216094f87ae9";
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", secretKey);
        
        userDetails = User.builder()
                .username("john@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertEquals(userDetails.getUsername(), jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_ShouldReturnTrue_ForValidToken() {
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 30))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);

        assertFalse(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForTokenWithDifferentUsername() {
        String token = Jwts.builder()
                .setSubject("differentemail@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                .compact();

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertFalse(isValid);
    }
}