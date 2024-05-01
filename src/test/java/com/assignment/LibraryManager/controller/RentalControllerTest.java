package com.assignment.LibraryManager.controller;

import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.dto.RentalDto;
import com.assignment.LibraryManager.entity.Book;
import com.assignment.LibraryManager.entity.Rental;
import com.assignment.LibraryManager.repository.BookRepository;
import com.assignment.LibraryManager.repository.RentalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class RentalControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void testCreateRental() {
        // Arrange
        RentalDto rentalDto = new RentalDto();
        rentalDto.setBookId(1);  // Assuming 1 is a valid book ID
        rentalDto.setRenterName("Ishan Kishan");
        rentalDto.setRentalDate(LocalDate.of(2023,7,23));
        rentalDto.setReturnDate(LocalDate.of(2021,5,24));



        ResponseEntity<RentalDto> response = restTemplate.postForEntity("/api/rentals", rentalDto, RentalDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(rentalDto.getBookId(), response.getBody().getBookId());
        assertEquals(rentalDto.getRenterName(), response.getBody().getRenterName());


        Optional<Rental> createdRentalOptional = rentalRepository.findById(response.getBody().getId());
        assertTrue(createdRentalOptional.isPresent());
        assertEquals(rentalDto.getBookId(), createdRentalOptional.get().getBookId());
        assertEquals(rentalDto.getRenterName(), createdRentalOptional.get().getRenterName());
    }
    @Test
    public void testGetRentalById() {

        Rental rental = new Rental();
        rental.setBookId(1);  // Assuming 1 is a valid book ID
        rental.setRenterName("Fighter Boy");
        Rental savedRental = rentalRepository.save(rental);


        ResponseEntity<RentalDto> response = restTemplate.getForEntity("/api/rentals/{id}", RentalDto.class, savedRental.getId());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedRental.getId(), response.getBody().getId());
        assertEquals(savedRental.getBookId(), response.getBody().getBookId());
        assertEquals(savedRental.getRenterName(), response.getBody().getRenterName());
    }
    @Test
    public void testGetBooksByAuthor() {

        Book book1 = new Book("Book1", "Author1", "ISBN1", LocalDate.now());
        Book book2 = new Book("Book2", "Author1", "ISBN2", LocalDate.now());
        bookRepository.saveAll(Arrays.asList(book1, book2));

        ResponseEntity<BookDto[]> response = restTemplate.getForEntity("/api/rentals/books/{authorName}", BookDto[].class, "Author1");


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        List<String> bookTitles = Arrays.stream(response.getBody()).map(BookDto::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.contains("Book1"));
        assertTrue(bookTitles.contains("Book2"));
    }
    @Test
    public void testGetAvailableBooks() {

        Book availableBook1 = new Book("olive", "Author1", "ISBN1", LocalDate.of(2023,2,12));
        Book availableBook2 = new Book("red", "Author2", "ISBN2", LocalDate.of(2021,5,23));
        Book unavailableBook = new Book("green", "Author3", "ISBN3", LocalDate.of(2022,4,25));
        bookRepository.saveAll(Arrays.asList(availableBook1, availableBook2, unavailableBook));


        ResponseEntity<BookDto[]> response = restTemplate.getForEntity("/api/rentals/available-books", BookDto[].class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        List<String> bookTitles = Arrays.stream(response.getBody()).map(BookDto::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.contains("AvailableBook1"));
        assertTrue(bookTitles.contains("AvailableBook2"));
        assertFalse(bookTitles.contains("UnavailableBook"));
    }
    @Test
    public void testGetRentedBooks() {

        Book rentedBook1 = new Book("olive", "Author1", "ISBN1", LocalDate.of(2023,2,12));
        Book rentedBook2 = new Book("red", "Author2", "ISBN2", LocalDate.of(2021,5,23));
        Book availableBook = new Book("green", "Author3", "ISBN3", LocalDate.of(2022,4,25));

        bookRepository.saveAll(Arrays.asList(rentedBook1, rentedBook2, availableBook));


        ResponseEntity<BookDto[]> response = restTemplate.getForEntity("/api/rentals/rented-books", BookDto[].class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        List<String> bookTitles = Arrays.stream(response.getBody()).map(BookDto::getTitle).collect(Collectors.toList());
        assertTrue(bookTitles.contains("RentedBook1"));
        assertTrue(bookTitles.contains("RentedBook2"));
        assertFalse(bookTitles.contains("AvailableBook"));
    }
}
