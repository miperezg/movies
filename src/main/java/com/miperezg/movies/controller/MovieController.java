package com.miperezg.movies.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

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
    public CollectionModel<EntityModel<Movie>> getAllMovies() {
        List<Movie> movies = service.getAllMovies();
        List<EntityModel<Movie>> movieResources = movies.stream()
            .map(movie -> EntityModel.of(movie,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getMovieById(movie.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());
        return CollectionModel.of(movieResources, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllMovies()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable Long id){
        Optional<Movie> movie = service.getMovieById(id);
        if (movie.isPresent()){
            EntityModel<Movie> model = EntityModel.of(movie.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getMovieById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllMovies()).withRel("all-movies"));
            return ResponseEntity.ok(model);
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Movie>> createMovie(@RequestBody Movie movie){
       Movie created = service.createMovie(movie);
       if(Objects.nonNull(created)){
            EntityModel<Movie> model = EntityModel.of(movie,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createMovie(movie)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllMovies()).withRel("all-movies")); 
            return ResponseEntity.ok(model);
       } else {
        return ResponseEntity.badRequest().build();
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        Optional<Movie> update  = Optional.ofNullable(service.updateMovie(movie, id));
        if (update.isPresent()){
            EntityModel<Movie> model = EntityModel.of(update.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateMovie(id, movie)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllMovies()).withRel("all-movies"));
            return ResponseEntity.ok(model);
        } 
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        service.deleteMovie(id);
    }
}
