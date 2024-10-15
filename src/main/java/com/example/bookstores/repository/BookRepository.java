package com.example.bookstores.repository;

import com.example.bookstores.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthor_NameContainingIgnoreCase(String authorName);
    List<Book> findByGenre_NameContainingIgnoreCase(String genreName);
}
