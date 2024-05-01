package com.assignment.LibraryManager.service;

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
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalServiceImplTest {

    @Autowired
    private RentalServiceImpl rentalService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void testCreateRental() {

        RentalDto rentalDTO = new RentalDto();
        rentalDTO.setBookId(1);
        rentalDTO.setRenterName("Rahul Bhai");

        RentalDto result = rentalService.createRental(rentalDTO);

        // Assert
        assertNotNull(result.getId());
        assertEquals(rentalDTO.getBookId(), result.getBookId());
        assertEquals(rentalDTO.getRenterName(), result.getRenterName());

        rentalRepository.deleteById(result.getId());
    }

    @Test
    public void testGetRentalById() {

        Rental rental = new Rental();
        rental.setBookId(1);
        rental.setRenterName("Kunu Bhai");
        Rental savedRental = rentalRepository.save(rental);

        RentalDto result = rentalService.getRentalById(savedRental.getId());


        assertNotNull(result);
        assertEquals(savedRental.getId(), result.getId());
        assertEquals(savedRental.getBookId(), result.getBookId());
        assertEquals(savedRental.getRenterName(), result.getRenterName());

        rentalRepository.deleteById(savedRental.getId());
    }

    @Test
    public void testGetBooksByAuthor() {
        // Arrange
        String authorName = "Author Name";
        Book book1 = new Book();
        book1.setAuthor(authorName);
        book1.setTitle("Book 1");
        book1.setAvailable(true);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setAuthor(authorName);
        book2.setTitle("Book 2");
        book2.setAvailable(false);
        bookRepository.save(book2);

        List<BookDto> result = rentalService.getBooksByAuthor(authorName);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(book -> book.getAuthor().equals(authorName)));

        bookRepository.deleteAll();
    }

    @Test
    public void testGetAvailableBooks() {

        Book availableBook1 = new Book();
        availableBook1.setAvailable(true);
        availableBook1.setTitle("Available Book 1");
        bookRepository.save(availableBook1);

        Book availableBook2 = new Book();
        availableBook2.setAvailable(true);
        availableBook2.setTitle("Available Book 2");
        bookRepository.save(availableBook2);

        List<BookDto> result = rentalService.getAvailableBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(book -> book.getTitle().startsWith("Available")));

        bookRepository.deleteAll();
    }

    @Test
    public void testGetRentedBooks() {
        Book rentedBook1 = new Book();
        rentedBook1.setAvailable(false);
        rentedBook1.setTitle("Rented Book 1");
        bookRepository.save(rentedBook1);

        Book rentedBook2 = new Book();
        rentedBook2.setAvailable(false);
        rentedBook2.setTitle("Rented Book 2");
        bookRepository.save(rentedBook2);

        List<BookDto> result = rentalService.getRentedBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(book -> book.getTitle().startsWith("Rented")));

        bookRepository.deleteAll();
    }

    @Test
    public void testGetOverdueRentals() {
        // Arrange
        Rental overdueRental1 = new Rental();
        overdueRental1.setRenterName("Jassie Bhai");
        overdueRental1.setRentalDate(LocalDate.now().minusDays(20));
        rentalRepository.save(overdueRental1);

        Rental overdueRental2 = new Rental();
        overdueRental2.setRenterName("Virat Kohli");
        overdueRental2.setRentalDate(LocalDate.now().minusDays(15));
        rentalRepository.save(overdueRental2);

        List<RentalDto> result = rentalService.getOverdueRentals();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(rental -> rental.getRenterName().startsWith("John") || rental.getRenterName().startsWith("Jane")));


        rentalRepository.deleteAll();
    }

    @Test
    public void testConvertToDTO() {

        Rental rental = new Rental();
        rental.setId(1);
        rental.setBookId(1);
        rental.setRenterName("Neha Bhasin");
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now());


        RentalDto result = rentalService.convertToDTO(rental);

        assertNotNull(result);
        assertEquals(rental.getId(), result.getId());
        assertEquals(rental.getBookId(), result.getBookId());
        assertEquals(rental.getRenterName(), result.getRenterName());
        assertEquals(rental.getRentalDate(), result.getRentalDate());
        assertEquals(rental.getReturnDate(), result.getReturnDate());
    }

    @Test
    public void testConvertBookToDTO() {

        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(LocalDate.of(2023, 03, 05));

        BookDto result = rentalService.convertBookToDTO(book);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getPublicationYear(), result.getPublicationYear());
    }


}