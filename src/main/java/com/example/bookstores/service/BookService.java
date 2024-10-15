package com.example.bookstores.service;

import com.example.bookstores.model.Book;
import com.example.bookstores.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String authorName) {
        return bookRepository.findByAuthor_NameContainingIgnoreCase(authorName);
    }

    public List<Book> searchByGenre(String genreName) {
        return bookRepository.findByGenre_NameContainingIgnoreCase(genreName);
    }
}