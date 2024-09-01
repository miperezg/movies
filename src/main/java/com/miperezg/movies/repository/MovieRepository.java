package com.miperezg.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miperezg.movies.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    
}
