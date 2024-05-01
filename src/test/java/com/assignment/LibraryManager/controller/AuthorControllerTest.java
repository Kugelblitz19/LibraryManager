package com.assignment.LibraryManager.controller;

import com.assignment.LibraryManager.dto.AuthorDto;
import com.assignment.LibraryManager.entity.Author;
import com.assignment.LibraryManager.repository.AuthorRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AuthorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthorRepository authorRepository;
    @Test
    public void testCreateAuthor() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(" Rahul Bhai");
        authorDto.setBiography("Level up bro");


        ResponseEntity<AuthorDto> response = restTemplate.postForEntity("/api/author", authorDto, AuthorDto.class);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(authorDto.getName(), response.getBody().getName());
        assertEquals(authorDto.getBiography(),response.getBody().getBiography());


        Optional<Author> createdAuthorOptional = authorRepository.findById(response.getBody().getId());
        assertTrue(createdAuthorOptional.isPresent());
        assertEquals(authorDto.getName(), createdAuthorOptional.get().getName());
        assertEquals(authorDto.getBiography(),createdAuthorOptional.get().getBiography());
    }
    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
    }
    @Test
    public void testGetAllAuthors() {
        //authorRepository.deleteAll();



        AuthorDto author1 = new AuthorDto(null, " Modi Ji", "Surya Bhai Don no 1");
        AuthorDto author2 = new AuthorDto(null, "Mota Bhai", "Very cunning one");

        authorRepository.saveAll(Arrays.asList(
                new Author(1, author1.getName(), author1.getBiography()),
                new Author(2, author2.getName(), author2.getBiography())
        ));


        ResponseEntity<AuthorDto[]> response = restTemplate.getForEntity("/api/author", AuthorDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);

        List<AuthorDto> authors = Arrays.asList(response.getBody());
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals(author1.getName())));
       // assertTrue(authors.stream().anyMatch(a->a.getBiography().equals(author1.getBiography())));
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals(author2.getName())));
       // assertTrue(authors.stream().anyMatch(a->a.getBiography().equals(author2.getBiography())));
    }
    @Test
    public void testGetAuthorById() {

        Author savedAuthor = authorRepository.save(new Author(1, "Seraa", "Sample biography"));


        ResponseEntity<AuthorDto> response = restTemplate.getForEntity("/api/author/{id}", AuthorDto.class, savedAuthor.getId());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedAuthor.getId(), response.getBody().getId());
        assertEquals(savedAuthor.getName(), response.getBody().getName());
        assertEquals(savedAuthor.getBiography(), response.getBody().getBiography());
    }
    @Test
    public void testUpdateAuthor() {
                Author savedAuthor = authorRepository.save(new Author(1, "JAI MAHAKAL", "Shiv Sambhu"));

        AuthorDto updatedAuthorDto = new AuthorDto(1, "HAR HAR MAHADEV", "Tri netra");


        ResponseEntity<AuthorDto> response = restTemplate.exchange(
                "/api/author/{id}", HttpMethod.PUT, new HttpEntity<>(updatedAuthorDto), AuthorDto.class, savedAuthor.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedAuthorDto.getName(), response.getBody().getName());
        assertEquals(updatedAuthorDto.getBiography(), response.getBody().getBiography());


        Optional<Author> updatedAuthorOptional = authorRepository.findById(savedAuthor.getId());
        assertTrue(updatedAuthorOptional.isPresent());
        assertEquals(updatedAuthorDto.getName(), updatedAuthorOptional.get().getName());
        assertEquals(updatedAuthorDto.getBiography(), updatedAuthorOptional.get().getBiography());
    }
    @Test
    public void testDeleteAuthor() {
        Author savedAuthor = authorRepository.save(new Author(1, "Raghu Nandan", "Dasrath Nandan"));


        restTemplate.delete("/api/author/{id}", savedAuthor.getId());


        assertFalse(authorRepository.findById(savedAuthor.getId()).isPresent());
    }

}
