package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.dto.RentalDto;
import com.assignment.LibraryManager.entity.Book;
import com.assignment.LibraryManager.entity.Rental;
import com.assignment.LibraryManager.repository.BookRepository;
import com.assignment.LibraryManager.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;


    public RentalDto createRental(RentalDto rentalDTO) {
        Rental rental = new Rental();
        rental.setBookId(rentalDTO.getBookId());
        rental.setRenterName(rentalDTO.getRenterName());
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now());

        Rental savedRental = rentalRepository.save(rental);
        return convertToDTO(savedRental);
    }

    public RentalDto getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        return convertToDTO(rental);
    }

    public List<BookDto> getBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthor(authorName);
        return books.stream().map(this::convertBookToDTO).collect(Collectors.toList());
    }

    public List<BookDto> getAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByAvailable(true);
        return availableBooks.stream().map(this::convertBookToDTO).collect(Collectors.toList());
    }

    public List<BookDto> getRentedBooks() {
        List<Book> rentedBooks = bookRepository.findByAvailable(false);
        return rentedBooks.stream().map(this::convertBookToDTO).collect(Collectors.toList());
    }


    public List<RentalDto> getOverdueRentals() {
        LocalDate dueDate = LocalDate.now().minusDays(14);
        List<Rental> overdueRentals = rentalRepository.findByReturnDateIsNullAndRentalDateBefore(dueDate);
        return overdueRentals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public RentalDto convertToDTO(Rental rental) {
        RentalDto rentalDTO = new RentalDto();
        rentalDTO.setId(rental.getId());
        rentalDTO.setBookId(rental.getBookId());
        rentalDTO.setRenterName(rental.getRenterName());
        rentalDTO.setRentalDate(rental.getRentalDate());
        rentalDTO.setReturnDate(rental.getReturnDate());
        return rentalDTO;
    }

    public BookDto convertBookToDTO(Book book) {
        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());
        return bookDTO;
    }
}
