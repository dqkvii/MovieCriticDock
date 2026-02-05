package com.aeribmm.filmcritic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FilmcriticApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmcriticApplication.class, args);
    }

}
