package com.assignment.LibraryManager.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class RentalEntityTests {
    @Test
    public void testRentalEntity() {
        Rental rental = new Rental();
        rental.setId(1);
        rental.setBookId(1);
        rental.setRenterName("John Doe");
        rental.setRentalDate(LocalDate.of(2022, 1, 1));
        rental.setReturnDate(LocalDate.of(2022, 1, 15));
        rental.setReturned(true);

        Book book = new Book();
        book.setId(1);
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        rental.setBook(book);

        assertEquals(1, rental.getId().intValue());
        assertEquals(1, rental.getBookId().intValue());
        assertEquals("John Doe", rental.getRenterName());
        assertEquals(LocalDate.of(2022, 1, 1), rental.getRentalDate());
        assertEquals(LocalDate.of(2022, 1, 15), rental.getReturnDate());
        assertTrue(rental.isReturned());
        assertEquals("Book 1", rental.getBook().getTitle());
        assertEquals("Author 1", rental.getBook().getAuthor());
    }

}
