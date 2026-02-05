package com.aeribmm.filmcritic.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Exception.userException.UserAlreadyExistsException;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void handleUserAlreadyExistsException_ShouldReturnConflictStatus() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        ResponseEntity<String> response = exceptionHandler.handleUserAlreadyExistsException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void handleUserNotFoundException_ShouldReturnNotFoundStatus() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<String> response = exceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleMovieNotFoundException_ShouldReturnNotFoundStatus() {
        MovieNotFoundException exception = new MovieNotFoundException();

        ResponseEntity<String> response = exceptionHandler.handleMovieNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}