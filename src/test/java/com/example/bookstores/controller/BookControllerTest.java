package com.example.bookstores.controller;



import com.example.bookstores.model.Book;
import com.example.bookstores.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBooks() throws Exception {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Outliers");

        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("The Great Gatsby"))
                .andExpect(jsonPath("$[1].title").value("Outliers"));
    }

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        // Arrange
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookService.save(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));
    }

    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby - Updated");

        when(bookService.save(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Great Gatsby - Updated"));
    }

    @Test
    void testDeleteBook() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    void testSearchBooksByTitle() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookService.searchByTitle("Gatsby")).thenReturn(List.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books/search/title")
                        .param("title", "Gatsby"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Great Gatsby"));
    }

    @Test
    void testSearchBooksByAuthor() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Outliers");

        when(bookService.searchByAuthor("Malcolm Gladwell")).thenReturn(List.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books/search/author")
                        .param("author", "Malcolm Gladwell"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Outliers"));
    }

    @Test
    void testSearchBooksByGenre() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookService.searchByGenre("Fiction")).thenReturn(List.of(book));

        // Act & Assert
        mockMvc.perform(get("/api/books/search/genre")
                        .param("genre", "Fiction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Great Gatsby"));
    }
}