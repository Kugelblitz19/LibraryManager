package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Integer id);

    BookDto createBook(BookDto bookDTO);

    BookDto updateBook(Integer id, BookDto bookDTO);

    void deleteBook(Integer id);
}
