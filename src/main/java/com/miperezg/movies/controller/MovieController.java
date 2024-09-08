package com.miperezg.movies.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.miperezg.movies.model.Movie;
import com.miperezg.movies.service.MovieService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getMethodName(@PathVariable Long id) {
        return service.getMovieById(id);
    }

    @PostMapping
    public Optional<Movie> createMovie(@RequestBody Movie movie){
       return Optional.ofNullable(service.createMovie(movie)); 
    }

    @PutMapping("/{id}")
    public Optional<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        return Optional.ofNullable(service.updateMovie(movie, id));
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        service.deleteMovie(id);
    }
}
