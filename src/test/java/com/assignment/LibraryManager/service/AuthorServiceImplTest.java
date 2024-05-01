package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.AuthorDto;
import com.assignment.LibraryManager.entity.Author;
import com.assignment.LibraryManager.exception.AuthorNotAvailableException;
import com.assignment.LibraryManager.repository.AuthorRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Initialize Mockito annotations
    }

    @Test
    public void testGetAllAuthors() {
        // Arrange
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "John Doe", "A famous author."));
        authors.add(new Author(2, "Jane Smith", "An aspiring writer."));

        // Mock the behavior of the authorRepository.findAll method
        when(authorRepository.findAll()).thenReturn(authors);

        // Act
        List<AuthorDto> authorDtos = authorService.getAllAuthors();

        // Assert
        assertEquals(authors.size(), authorDtos.size());
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            AuthorDto authorDto = authorDtos.get(i);
            assertEquals(author.getId(), authorDto.getId());
            assertEquals(author.getName(), authorDto.getName());
            assertEquals(author.getBiography(), authorDto.getBiography());
        }
    }
    @Test
    public void testConvertToDTO() {
        // Arrange
        Author author = new Author();
        author.setId(1);
        author.setName("John Doe");
        author.setBiography("A famous author.");


        // Act
        AuthorDto authorDto = authorService.convertToDTO(author);

        // Assert
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
        assertEquals(author.getBiography(), authorDto.getBiography());
    }

    @Test
    public void testConvertToEntity() {
        // Arrange
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1);
        authorDto.setName("Jane Smith");
        authorDto.setBiography("An aspiring writer.");

        // Act
        Author author = authorService.convertToEntity(authorDto);

        // Assert
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getBiography(), author.getBiography());
    }
    @Test
    public void testCreateAuthor() {
        // Arrange
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1);
        authorDto.setName("John Doe");
        authorDto.setBiography("A famous author.");

        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBiography(authorDto.getBiography());

        // Mock the behavior of authorRepository.save method
        when(authorRepository.save(author)).thenReturn(author);

        // Act
        AuthorDto result = authorService.createAuthor(authorDto);

        // Assert
        assertEquals(authorDto.getId(), result.getId());
        assertEquals(authorDto.getName(), result.getName());
        assertEquals(authorDto.getBiography(), result.getBiography());
    }
    @Test
    public void testGetAuthorById() {
        // Arrange
        Integer id = 1;
        Author author = new Author();
        author.setId(id);
        author.setName("Rahul Dev");
        author.setBiography("Innovation of joy");

        // Mock the behavior of the authorRepository.findById method
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        // Act
        AuthorDto authorDto = authorService.getAuthorById(id);

        // Assert
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
        assertEquals(author.getBiography(),authorDto.getBiography());
    }

    @Test
    public void testGetAuthorById_WhenAuthorNotFound() {
        // Arrange
        Integer id = 1;

        // Mock the behavior of the authorRepository.findById method
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AuthorNotAvailableException.class, () -> authorService.getAuthorById(id));
    }
    @Test
    public void testUpdateAuthor() {
        // Arrange
        Integer id = 1;
        Author existingAuthor = new Author();
        existingAuthor.setId(id);
        existingAuthor.setName("John Doe");
        existingAuthor.setBiography("A famous author.");

        AuthorDto updatedAuthorDto = new AuthorDto();
        updatedAuthorDto.setId(id);
        updatedAuthorDto.setName("Jane Smith");
        updatedAuthorDto.setBiography("An aspiring writer.");

        // Mock the behavior of the authorRepository.findById and authorRepository.save methods
        when(authorRepository.findById(id)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        // Act
        AuthorDto result = authorService.updateAuthor(id, updatedAuthorDto);

        // Assert
        assertEquals(updatedAuthorDto.getId(), result.getId());
        assertEquals(updatedAuthorDto.getName(), result.getName());
        assertEquals(updatedAuthorDto.getBiography(), result.getBiography());
    }
    @Test
    public void testDeleteAuthor() {
        // Arrange
        Integer id = 1;

        // Act
        authorService.deleteAuthor(id);

        // Assert
        verify(authorRepository, times(1)).deleteById(id);
    }

}
