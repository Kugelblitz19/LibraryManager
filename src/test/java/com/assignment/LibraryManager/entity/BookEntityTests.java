package com.assignment.LibraryManager.entity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class BookEntityTests {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testBookEntityMapping() {

        Author author = new Author();
        author.setName("John Doe");
        entityManager.persist(author);

        Book book = new Book();
        book.setTitle("Journey");
        book.setAuthor("Virat");
        book.setIsbn("1234567890");
        book.setPublicationYear(LocalDate.of(2021, 1, 1));
        book.setAvailable(true);

        // When
        entityManager.persistAndFlush(book);

        // Then
        Book foundBook = entityManager.find(Book.class, book.getId());
        assertThat(foundBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(foundBook.getPublicationYear()).isEqualTo(book.getPublicationYear());
        assertThat(foundBook.isAvailable()).isEqualTo(book.isAvailable());
    }


}
