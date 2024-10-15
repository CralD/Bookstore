package com.example.bookstores.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bookstores.model.Genre;
import com.example.bookstores.repository.GenreRepository;
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

class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Non-Fiction");

        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre1, genre2));

        // Act
        List<Genre> result = genreService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Fiction", result.get(0).getName());
        assertEquals("Non-Fiction", result.get(1).getName());
    }

    @Test
    void testFindById() {
        // Arrange
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        // Act
        Optional<Genre> result = genreService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Fiction", result.get().getName());
    }

    @Test
    void testSave() {
        // Arrange
        Genre genre = new Genre();
        genre.setName("Fiction");

        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        // Act
        Genre savedGenre = genreService.save(genre);

        // Assert
        assertNotNull(savedGenre);
        assertEquals("Fiction", savedGenre.getName());

        // Verify the interaction with the repository
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void testDeleteById() {
        // Act
        genreService.deleteById(1L);

        // Assert
        verify(genreRepository, times(1)).deleteById(1L);
    }
}