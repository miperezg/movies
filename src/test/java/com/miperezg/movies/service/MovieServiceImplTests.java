package com.miperezg.movies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import com.miperezg.movies.model.Movie;
import com.miperezg.movies.repository.MovieRepository;

@SpringBootTest
public class MovieServiceImplTests {

    @MockBean
    private MovieRepository repository;

    @Autowired
    private MovieServiceImpl service;

    @BeforeEach
    public void setup(){
        List<Movie> movies = new ArrayList<Movie>();
        Movie movie = new Movie();
        movie.setId(Integer.toUnsignedLong(1));
        movies.add(movie);
        Mockito.when(repository.findAll()).thenReturn(movies);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        Mockito.when(repository.save(any(Movie.class))).thenAnswer(a -> a.getArgument(0));
        Mockito.doNothing().when(repository).deleteById(1L);
    }

    @Test
    public void getAllMoviesTest(){
        var movies = service.getAllMovies();
        Assert.notNull(movies, "service must not return null");
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getMovieByIdTest(){
        var movie = service.getMovieById(1L);
        assumeTrue(movie.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void createMovieTest(){
        Movie createMovie = new Movie();
        createMovie.setId(1L);
        Movie movie = service.createMovie(createMovie);
        assertNotNull(createMovie);
        assertEquals(createMovie.getId(), movie.getId());

        verify(repository, times(1)).save(any(Movie.class));
    }

    @Test
    public void updateMovie_ifExists(){
        Movie updateMovie = new Movie(); 
        updateMovie.setTitle("update"); 
        Movie movie = service.updateMovie(updateMovie, 1L);
        assertNotNull(movie);
        assertEquals(updateMovie.getTitle(), movie.getTitle());

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(any(Movie.class));
    }

    @Test
    public void updateMovie_notExists(){
        // To overwrite the default mock.
        Mockito.when(repository.existsById(1L)).thenReturn(false);
        Movie updateMovie = new Movie();
        Movie movie = service.updateMovie(updateMovie, 1L);
        assertNull(movie);

        verify(repository, times(1)).existsById(1L);

    }

    @Test
    public void deleteMovieTest(){
        service.deleteMovie(1L); 
        verify(repository, times(1)).deleteById(1L);
    }

    @AfterEach
    public void resetEach(){
        reset(repository);
    }
}
