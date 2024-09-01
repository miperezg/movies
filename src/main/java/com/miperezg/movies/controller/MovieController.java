package com.miperezg.movies.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.miperezg.movies.model.Movie;
import com.miperezg.movies.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/movies/{id}")
    public Optional<Movie> getMethodName(@PathVariable Long id) {
        return service.getMovieById(id);
    }
}
