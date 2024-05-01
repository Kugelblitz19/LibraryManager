package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.BookDto;
import com.assignment.LibraryManager.entity.Book;
import com.assignment.LibraryManager.exception.BookNotAvailableException;
import com.assignment.LibraryManager.repository.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertToDTO() {
        // Arrange
        Book book = new Book();
        book.setId(1);
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setIsbn("1234567890");
        book.setPublicationYear(LocalDate.of(2022, 1, 1));


        BookDto bookDto = bookService.convertToDTO(book);

        assertNotNull(bookDto);
        // assertEquals(1,bookDto.getId());
        assertEquals("Book 1", bookDto.getTitle());
        assertEquals("Author 1", bookDto.getAuthor());
        assertEquals("1234567890", bookDto.getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), bookDto.getPublicationYear());
    }

    @Test
    public void testConvertToEntity() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("Book 1");
        bookDto.setAuthor("Author 1");
        bookDto.setIsbn("1234567890");
        bookDto.setPublicationYear(LocalDate.of(2022, 1, 1));


        Book book = bookService.convertToEntity(bookDto);


        assertNotNull(book);

        assertEquals("Book 1", book.getTitle());
        assertEquals("Author 1", book.getAuthor());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), book.getPublicationYear());
    }
    @Test
    public void testGetAllBooks() {
        // Arrange
        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", "Author 1", "1234567890", LocalDate.of(2022, 1, 1)),
                new Book(2, "Book 2", "Author 2", "2345678901", LocalDate.of(2022, 2, 2))
        );

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<BookDto> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("1234567890", result.get(0).getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), result.get(0).getPublicationYear());

        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("2345678901", result.get(1).getIsbn());
        assertEquals(LocalDate.of(2022, 2, 2), result.get(1).getPublicationYear());

        verify(bookRepository).findAll();
    }


    @Test
   public void testGetBookById() {
        // Arrange
        Integer bookId = 1;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(LocalDate.of(2022, 1, 1));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        BookDto foundBook = bookService.getBookById(bookId);

        // Assert
        assertEquals("Test Book", foundBook.getTitle());
        assertEquals("Test Author", foundBook.getAuthor());
        assertEquals("1234567890", foundBook.getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), foundBook.getPublicationYear());

        verify(bookRepository).findById(bookId);
    }

    @Test
   public void testGetBookByIdBookNotAvailableException() {
        // Arrange
        Integer bookId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BookNotAvailableException.class, () -> bookService.getBookById(bookId));
    }

    @Test
   public void testCreateBook() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setIsbn("1234567890");
        bookDto.setPublicationYear(LocalDate.of(2022, 1, 1));

        Book book = new Book();
     //   book.setId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(LocalDate.of(2022, 1, 1));

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookDto savedBookDto = bookService.createBook(bookDto);

        // Assert
        assertEquals(bookDto.getTitle(), savedBookDto.getTitle());
        assertEquals(bookDto.getAuthor(), savedBookDto.getAuthor());
        assertEquals(bookDto.getIsbn(), savedBookDto.getIsbn());
        assertEquals(bookDto.getPublicationYear(), savedBookDto.getPublicationYear());

        verify(bookRepository).save(book);
    }
    @Test
  public   void testUpdateBook() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("New Title");
        bookDto.setAuthor("New Author");
        bookDto.setIsbn("New ISBN");
        bookDto.setPublicationYear(LocalDate.of(2022, 1, 1));

        Book existingBook = new Book();
        existingBook.setId(1);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Old Author");
        existingBook.setIsbn("Old ISBN");
        existingBook.setPublicationYear(LocalDate.of(2021, 1, 1));

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        // Act
        BookDto updatedBook = bookService.updateBook(1, bookDto);

        // Assert
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("New Author", updatedBook.getAuthor());
        assertEquals("New ISBN", updatedBook.getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), updatedBook.getPublicationYear());

        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
   public void testUpdateBookBookNotAvailableException() {
        // Arrange
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BookNotAvailableException.class, () -> bookService.updateBook(1, new BookDto()));
    }
    @Test
  public   void testDeleteBook() {

        Integer bookId = 1;


        bookService.deleteBook(bookId);

        verify(bookRepository).deleteById(bookId);
    }
}