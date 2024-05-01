package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.AuthorDto;
import com.assignment.LibraryManager.entity.Author;
import com.assignment.LibraryManager.exception.AuthorNotAvailableException;
import com.assignment.LibraryManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    @Override
    public AuthorDto getAuthorById(Integer id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotAvailableException("Author not found"));
        return convertToDTO(author);
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = convertToEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    @Override
    public AuthorDto updateAuthor(Integer id, AuthorDto authorDto) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new AuthorNotAvailableException("Author not found"));
        existingAuthor.setName(authorDto.getName());
        existingAuthor.setBiography(authorDto.getBiography());

        Author updatedBook = authorRepository.save(existingAuthor);
        return convertToDTO(updatedBook);
    }

    @Override
    public void deleteAuthor(Integer id) {
        authorRepository.deleteById(id);
    }

    AuthorDto convertToDTO(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBiography(author.getBiography());

        return authorDto;
    }

    Author convertToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBiography(authorDto.getBiography());

        return author;
    }
}
