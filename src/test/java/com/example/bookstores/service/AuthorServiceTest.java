package com.example.bookstores.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bookstores.model.Author;
import com.example.bookstores.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("F. Scott Fitzgerald");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Malcolm Gladwell");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // Act
        List<Author> result = authorService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("F. Scott Fitzgerald", result.get(0).getName());
        assertEquals("Malcolm Gladwell", result.get(1).getName());
    }

    @Test
    void testFindById() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setName("F. Scott Fitzgerald");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Act
        Optional<Author> result = authorService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("F. Scott Fitzgerald", result.get().getName());
    }

    @Test
    void testSave() {
        // Arrange
        Author author = new Author();
        author.setName("F. Scott Fitzgerald");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // Act
        Author savedAuthor = authorService.save(author);

        // Assert
        assertNotNull(savedAuthor);
        assertEquals("F. Scott Fitzgerald", savedAuthor.getName());

        // Verify the interaction with the repository
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testDeleteById() {
        // Act
        authorService.deleteById(1L);

        // Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }
}