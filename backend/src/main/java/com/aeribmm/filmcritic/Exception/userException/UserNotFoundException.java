package com.aeribmm.filmcritic.Exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String massage) {
        super(massage);
    }

    public UserNotFoundException() {
    }
}
