package com.assignment.LibraryManager.controller;

import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.dto.RentalDto;
import com.assignment.LibraryManager.service.RentalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    @Autowired
    private RentalServiceImpl rentalService;

    @PostMapping
    public ResponseEntity<RentalDto> createRental(@Valid @RequestBody RentalDto rentalDTO) {
        RentalDto createdRental = rentalService.createRental(rentalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Integer id) {
        RentalDto rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }
    @GetMapping("/books/{authorName}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable String authorName) {
        List<BookDto> booksByAuthor = rentalService.getBooksByAuthor(authorName);
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping("/available-books")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        List<BookDto> availableBooks = rentalService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    @GetMapping("/rented-books")
    public ResponseEntity<List<BookDto>> getRentedBooks() {
        List<BookDto> rentedBooks = rentalService.getRentedBooks();
        return ResponseEntity.ok(rentedBooks);
    }

    @GetMapping("/overdue-rentals")
    public ResponseEntity<List<RentalDto>> getOverdueRentals() {
        List<RentalDto> overdueRentals = rentalService.getOverdueRentals();
        return ResponseEntity.ok(overdueRentals);
    }

}