package com.assignment.LibraryManager.controller;

import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.entity.Book;
import com.assignment.LibraryManager.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testGetAllBooks() {

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Kunal");
        book1.setIsbn("23455");
        book1.setPublicationYear(LocalDate.of(2023,9,02));
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Bibhu");
        book2.setIsbn("2455");
        book2.setPublicationYear(LocalDate.of(2024,9,6));
        bookRepository.save(book2);


        ResponseEntity<List<BookDto>> response = restTemplate.exchange(
                "/api/book",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookDto>>() {});


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
    @Test
    public void testGetBookById() {

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setIsbn("545465");
        book.setPublicationYear(LocalDate.of(2024,05,04));
        Book savedBook = bookRepository.save(book);


        ResponseEntity<BookDto> response = restTemplate.getForEntity("/api/book/{id}", BookDto.class, savedBook.getId());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedBook.getId(), response.getBody().getId());
        assertEquals(savedBook.getTitle(), response.getBody().getTitle());
        assertEquals(savedBook.getAuthor(), response.getBody().getAuthor());
        assertEquals(savedBook.getIsbn(),response.getBody().getIsbn());
        assertEquals(savedBook.getPublicationYear(),response.getBody().getPublicationYear());
    }
    @Test
    public void testCreateBook() {
        bookRepository.deleteAll();

        BookDto bookDto = new BookDto();
        bookDto.setTitle("New Book");
        bookDto.setAuthor("New Author");
        bookDto.setIsbn("898");
        bookDto.setPublicationYear(LocalDate.of(2024,8,19));

        ResponseEntity<BookDto> response = restTemplate.postForEntity("/api/book", bookDto, BookDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookDto.getTitle(), response.getBody().getTitle());
        assertEquals(bookDto.getAuthor(), response.getBody().getAuthor());
        assertEquals(bookDto.getIsbn(),response.getBody().getIsbn());
        assertEquals(bookDto.getPublicationYear(),response.getBody().getPublicationYear());

        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        assertEquals(bookDto.getTitle(), books.get(0).getTitle());
        assertEquals(bookDto.getAuthor(), books.get(0).getAuthor());
        assertEquals(bookDto.getIsbn(),books.get(0).getIsbn());
        assertEquals(bookDto.getPublicationYear(),books.get(0).getPublicationYear());
    }
    @Test
    public void testUpdateBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setIsbn("8978");
        book.setPublicationYear(LocalDate.of(2021,4,23));
        Book savedBook = bookRepository.save(book);

        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setTitle("Updated Title");
        updatedBookDto.setAuthor("Updated Author");

        // Act
        restTemplate.put("/api/book/{id}", updatedBookDto, savedBook.getId());

        // Assert
        Optional<Book> updatedBookOptional = bookRepository.findById(savedBook.getId());
        assertTrue(updatedBookOptional.isPresent());
        assertEquals(updatedBookDto.getTitle(), updatedBookOptional.get().getTitle());
        assertEquals(updatedBookDto.getAuthor(), updatedBookOptional.get().getAuthor());
    }
    @Test
    public void testDeleteBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");
        book.setIsbn("8990");
        book.setPublicationYear(LocalDate.of(2021,8,13));
        Book savedBook = bookRepository.save(book);

        restTemplate.delete("/api/book/{id}", savedBook.getId());

        assertFalse(bookRepository.existsById(savedBook.getId()));
    }
}
