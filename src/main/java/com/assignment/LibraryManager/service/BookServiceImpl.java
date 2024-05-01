package com.assignment.LibraryManager.service;


import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.entity.Book;
import com.assignment.LibraryManager.exception.BookNotAvailableException;
import com.assignment.LibraryManager.repository.AuthorRepository;
import com.assignment.LibraryManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotAvailableException("Book not found"));
        return convertToDTO(book);
    }

    @Override
    public BookDto createBook(BookDto bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDto updateBook(Integer id, BookDto bookDTO) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotAvailableException("Book not found"));
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public   Book convertToEntity(BookDto bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationYear(bookDTO.getPublicationYear());
        return book;
    }
    public BookDto convertToDTO(Book book) {
        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        // System.out.println("Author: " + book.getTitle());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationYear(book.getPublicationYear());
        return bookDTO;
    }



}