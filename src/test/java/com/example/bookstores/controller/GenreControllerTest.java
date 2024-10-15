package com.example.bookstores.controller;



import com.example.bookstores.model.Genre;
import com.example.bookstores.service.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllGenres() throws Exception {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Non-fiction");

        when(genreService.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        // Act & Assert
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fiction"))
                .andExpect(jsonPath("$[1].name").value("Non-fiction"));
    }

    @Test
    void testGetGenreById() throws Exception {
        // Arrange
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        when(genreService.findById(1L)).thenReturn(Optional.of(genre));

        // Act & Assert
        mockMvc.perform(get("/api/genres/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fiction"));
    }

    @Test
    void testGetGenreById_NotFound() throws Exception {
        // Arrange
        when(genreService.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/genres/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateGenre() throws Exception {
        // Arrange
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy");

        when(genreService.save(any(Genre.class))).thenReturn(genre);

        // Act & Assert
        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Fantasy"));
    }

    @Test
    void testUpdateGenre() throws Exception {
        // Arrange
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy Updated");

        when(genreService.save(any(Genre.class))).thenReturn(genre);

        // Act & Assert
        mockMvc.perform(put("/api/genres/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fantasy Updated"));
    }

    @Test
    void testDeleteGenre() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/genres/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(genreService, times(1)).deleteById(1L);
    }
}