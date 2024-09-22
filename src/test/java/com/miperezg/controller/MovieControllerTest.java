package com.miperezg.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.miperezg.movies.controller.MovieController;
import com.miperezg.movies.model.Movie;
import com.miperezg.movies.service.MovieServiceImpl;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieServiceImpl service;

    @Test
    public void getAllMovies() throws Exception{
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Sonic The Hedgehog");
        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Super Mario Bros.");
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(movie1);
        movies.add(movie2);
        when(service.getAllMovies()).thenReturn(movies);
        
        // Act & Assert
        mvc.perform(MockMvcRequestBuilders.get("/movies"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Sonic The Hedgehog")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Super Mario Bros.")));

    }
    
}
