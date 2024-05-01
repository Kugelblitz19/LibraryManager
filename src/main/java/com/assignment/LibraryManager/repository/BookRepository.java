package com.assignment.LibraryManager.repository;

import com.assignment.LibraryManager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String authorName);

    List<Book> findByAvailable(boolean b);
    //  List<Book> findByRentedTrue();

    // List<Book> findByRentedFalse();
    //  Book findByBookId(Integer id);
}
