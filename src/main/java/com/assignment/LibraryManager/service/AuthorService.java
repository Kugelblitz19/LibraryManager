package com.assignment.LibraryManager.service;

import com.assignment.LibraryManager.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Integer id);

    AuthorDto createAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(Integer id, AuthorDto authorDto);

    void deleteAuthor(Integer id);

}
