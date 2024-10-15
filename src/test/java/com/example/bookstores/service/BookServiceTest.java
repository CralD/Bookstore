package com.example.bookstores.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bookstores.model.Book;
import com.example.bookstores.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Outliers");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Book> result = bookService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
        assertEquals("Outliers", result.get(1).getTitle());
    }

    @Test
    void testFindById() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("The Great Gatsby", result.get().getTitle());
    }

    @Test
    void testSave() {
        // Arrange
        Book book = new Book();
        book.setTitle("The Great Gatsby");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book savedBook = bookService.save(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals("The Great Gatsby", savedBook.getTitle());

        // Verify the interaction with the repository
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteById() {
        // Act
        bookService.deleteById(1L);

        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchByTitle() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Outliers");

        when(bookRepository.findByTitleContainingIgnoreCase("Gatsby"))
                .thenReturn(Arrays.asList(book1));

        // Act
        List<Book> result = bookService.searchByTitle("Gatsby");

        // Assert
        assertEquals(1, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
    }

    @Test
    void testSearchByAuthor() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");
        // Assuming an author name is set in the Book entity (not shown here)
        // book1.setAuthor(new Author("F. Scott Fitzgerald"));

        when(bookRepository.findByAuthor_NameContainingIgnoreCase("Fitzgerald"))
                .thenReturn(Arrays.asList(book1));

        // Act
        List<Book> result = bookService.searchByAuthor("Fitzgerald");

        // Assert
        assertEquals(1, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
    }

    @Test
    void testSearchByGenre() {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Great Gatsby");
        // Assuming a genre is set in the Book entity (not shown here)
        // book1.setGenre(new Genre("Fiction"));

        when(bookRepository.findByGenre_NameContainingIgnoreCase("Fiction"))
                .thenReturn(Arrays.asList(book1));

        // Act
        List<Book> result = bookService.searchByGenre("Fiction");

        // Assert
        assertEquals(1, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
    }
}