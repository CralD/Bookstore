package com.example.bookstores.controller;



import com.example.bookstores.model.Author;
import com.example.bookstores.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAuthors() throws Exception {
        // Arrange
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("F. Scott Fitzgerald");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Malcolm Gladwell");

        when(authorService.findAll()).thenReturn(Arrays.asList(author1, author2));

        // Act & Assert
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$[1].name").value("Malcolm Gladwell"));
    }

    @Test
    void testGetAuthorById() throws Exception {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("F. Scott Fitzgerald");

        when(authorService.findById(1L)).thenReturn(Optional.of(author));

        // Act & Assert
        mockMvc.perform(get("/api/authors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("F. Scott Fitzgerald"));
    }

    @Test
    void testGetAuthorById_NotFound() throws Exception {
        // Arrange
        when(authorService.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/authors/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAuthor() throws Exception {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("F. Scott Fitzgerald");

        when(authorService.save(any(Author.class))).thenReturn(author);

        // Act & Assert
        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("F. Scott Fitzgerald"));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("Updated Author");

        when(authorService.save(any(Author.class))).thenReturn(author);

        // Act & Assert
        mockMvc.perform(put("/api/authors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Author"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/authors/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteById(1L);
    }
}