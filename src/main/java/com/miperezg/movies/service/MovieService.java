package com.miperezg.movies.service;

import java.util.List;
import java.util.Optional;

import com.miperezg.movies.model.*;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Long id);
    Movie createMovie(Movie movie);
    Movie updateMovie(Movie movie, Long id);
    void deleteMovie(Long id);
}
